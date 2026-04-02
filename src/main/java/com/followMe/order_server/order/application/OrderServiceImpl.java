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
import com.followMe.order_server.order.domain.exception.StockShortageException;
import com.followMe.order_server.order.domain.repository.OrderRepository;
import com.followMe.order_server.order.infrastructure.client.delivery.DeliveryClientAdapter;
import com.followMe.order_server.order.infrastructure.client.delivery.dto.response.DeliveryCreateResponse;
import com.followMe.order_server.order.infrastructure.client.hub.HubClientAdapter;
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

  @Override
  @Transactional
  public void create(OrderCreateRequest request) {

    ProductInfo productInfo =
        new ProductInfo(request.productId(), request.productName(), request.quantity());

    VendorInfo requestVendor =
        new VendorInfo(
            request.requestVendorId(), request.requestVendorName(), request.requestVendorHubId());

    VendorInfo receiverVendor =
        new VendorInfo(
            request.receiverVendorId(),
            request.receiverVendorName(),
            request.receiverVendorHubId());

    UUID orderId = UUID.randomUUID();

    if (!hubClientAdapter
        .decreaseStockIfAvailable(orderId, productInfo.getProductId(), productInfo.getQuantity())
        .success()) {
      throw new StockShortageException();
    }
    ;

    DeliveryCreateResponse deliveryCreateResponse =
        deliveryClientAdapter.createDelivery(
            orderId, requestVendor.getHubId(), receiverVendor.getVendorId());
    Order order =
        Order.ofCreate(
            orderId,
            deliveryCreateResponse.deliveryId(),
            deliveryCreateResponse.deliveryManagerId(),
            productInfo,
            requestVendor,
            receiverVendor,
            request.requestNote());

    orderRepository.save(order);
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
  public void update(UUID orderId, OrderUpdateRequest updateRequest) {
    Order order = orderRepository.findById(orderId);

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
  public void softDeleteById(UUID orderId) {
    Order order = orderRepository.findById(orderId);
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
  public void cancel(UUID orderId, UUID userId) {
    Order order = orderRepository.findById(orderId);
    order.cancel(userId);
    hubClientAdapter.rollbackStock(
        orderId, order.getProductInfo().getProductId(), order.getProductInfo().getQuantity());
  }
}
