package com.followMe.order_server.order.application;

import com.followMe.common.pagination.CursorRequest;
import com.followMe.common.pagination.CursorResponse;
import com.followMe.order_server.order.application.dto.request.OrderCreateRequest;
import com.followMe.order_server.order.application.dto.request.OrderSearchCondition;
import com.followMe.order_server.order.application.dto.request.OrderUpdateDeliveryManagerRequest;
import com.followMe.order_server.order.application.dto.request.OrderUpdateRequest;
import com.followMe.order_server.order.application.dto.request.OrderUpdateStateRequest;
import com.followMe.order_server.order.application.dto.request.UserContext;
import com.followMe.order_server.order.application.dto.response.OrderResponse;
import com.followMe.order_server.order.domain.Order;
import com.followMe.order_server.order.domain.OrderState;
import com.followMe.order_server.order.domain.ProductInfo;
import com.followMe.order_server.order.domain.VendorInfo;
import com.followMe.order_server.order.domain.repository.OrderRepository;
import com.followMe.order_server.order.domain.service.OrderPermissionChecker;
import com.followMe.order_server.order.infrastructure.client.delivery.DeliveryClientAdapter;
import com.followMe.order_server.order.infrastructure.client.delivery.dto.response.DeliveryCreateResponse;
import com.followMe.order_server.order.infrastructure.client.hub.HubClientAdapter;
import com.followMe.order_server.order.infrastructure.client.slack.MessageConstructor;
import com.followMe.order_server.order.infrastructure.client.slack.SlackClientAdapter;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final HubClientAdapter hubClientAdapter;
  private final DeliveryClientAdapter deliveryClientAdapter;
  private final SlackClientAdapter slackClientAdapter;
  private final OrderPermissionChecker orderPermissionChecker;

  @Override
  @Transactional
  public void create(OrderCreateRequest request) {

    ProductInfo productInfo =
        new ProductInfo(request.productId(), request.productName(), request.quantity());

    VendorInfo resourceVendor =
        new VendorInfo(
            request.resourceVendorId(),
            request.resourceVendorName(),
            request.resourceVendorHubId(),
            request.resourceVendorHubName());

    VendorInfo receiverVendor =
        new VendorInfo(
            request.receiverVendorId(),
            request.receiverVendorName(),
            request.receiverVendorHubId(),
            request.receiverVendorHubName());

    UUID orderId = UUID.randomUUID();

    hubClientAdapter.decreaseStockIfAvailable(
        orderId, productInfo.getProductId(), productInfo.getQuantity());

    try {
      DeliveryCreateResponse deliveryCreateResponse =
          deliveryClientAdapter.createDelivery(
              orderId, resourceVendor.getHubId(), receiverVendor.getVendorId());

      Order order =
          Order.ofCreate(
              orderId,
              deliveryCreateResponse.deliveryId(),
              deliveryCreateResponse.deliveryManagerId(),
              productInfo,
              resourceVendor,
              receiverVendor,
              request.requestNote());

      orderRepository.save(order);

      sendSlack(order, deliveryCreateResponse);
    } catch (Exception e) {
      hubClientAdapter.rollbackStock(
          orderId, productInfo.getProductId(), productInfo.getQuantity());
      throw new RuntimeException(e);
    }
  }

  private void sendSlack(Order order, DeliveryCreateResponse deliveryCreateResponse) {
    String message =
        MessageConstructor.generateOrderCreatedMessage(
            order,
            deliveryCreateResponse.waypoints(),
            deliveryCreateResponse.deliveryManagerName());

    slackClientAdapter.sendSlack(order.getCurrentDeliveryManagerId(), order.getOrderId(), message);
  }

  @Override
  @Transactional(readOnly = true)
  public OrderResponse readById(UUID orderId) {
    Order order = orderRepository.findById(orderId);
    return OrderResponse.from(order);
  }

  @Override
  @Transactional(readOnly = true)
  public CursorResponse<OrderResponse> search(
      CursorRequest cursorRequest, OrderSearchCondition condition, UserContext userContext) {
    OrderSearchCondition filteredCondition =
        OrderSearchFilter.applyRoleFilter(condition, userContext);

    List<Order> orders =
        orderRepository.searchByCursor(
            cursorRequest.getCursor(), cursorRequest.getSize(), filteredCondition);

    return CursorResponse.of(
        orders,
        cursorRequest.getSize(),
        OrderResponse::from,
        order -> order.getOrderId().toString());
  }

  @Override
  @Transactional
  public void update(UUID orderId, OrderUpdateRequest updateRequest, UserContext userContext) {
    Order order = orderRepository.findById(orderId);
    orderPermissionChecker.checkUpdatePermission(userContext, order);
    if (updateRequest.requestNote() != null) {
      order.updateRequestNote(updateRequest.requestNote());
    }

    if (updateRequest.quantity() != null) {
      hubClientAdapter.decreaseStockIfAvailable(
          orderId, order.getProductInfo().getProductId(), updateRequest.quantity());
      order.updateQuantity(updateRequest.quantity());
    }
  }

  @Override
  @Transactional
  public void updateStatus(UUID orderId, OrderUpdateStateRequest updateStateRequest) {
    Order order = orderRepository.findById(orderId);
    OrderState updatedState = OrderState.valueOf(updateStateRequest.status().toUpperCase());
    order.updateState(updatedState);
  }

  @Override
  @Transactional
  public void softDeleteById(UUID orderId, UserContext userContext) {
    Order order = orderRepository.findById(orderId);
    orderPermissionChecker.checkDeletePermission(userContext, order);
    order.softDelete(orderId);
  }

  @Override
  @Transactional
  public void updateDeliveryManager(
      UUID orderId, OrderUpdateDeliveryManagerRequest updateDeliveryManagerRequest) {
    Order order = orderRepository.findById(orderId);
    order.updateDeliveryManager(updateDeliveryManagerRequest.deliveryManagerId());
  }

  @Override
  @Transactional
  public void cancel(UUID orderId, UserContext userContext) {
    Order order = orderRepository.findById(orderId);
    orderPermissionChecker.checkCancelPermission(userContext, order);
    order.cancel(userContext.userId());
    hubClientAdapter.rollbackStock(
        orderId, order.getProductInfo().getProductId(), order.getProductInfo().getQuantity());
    deliveryClientAdapter.cancelDelivery(order.getDeliveryId());
  }
}
