package com.followMe.order_server.order.application;

import com.followMe.common.pagination.CursorRequest;
import com.followMe.common.pagination.CursorResponse;
import com.followMe.order_server.order.application.dto.request.OrderCreateRequest;
import com.followMe.order_server.order.application.dto.request.OrderSearchCondition;
import com.followMe.order_server.order.application.dto.request.UserContext;
import com.followMe.order_server.order.application.dto.response.OrderResponse;
import com.followMe.order_server.order.domain.Order;
import com.followMe.order_server.order.domain.ProductInfo;
import com.followMe.order_server.order.domain.VendorInfo;
import com.followMe.order_server.order.domain.repository.OrderRepository;
import com.followMe.order_server.order.infrastructure.client.delivery.DeliveryClientAdapter;
import com.followMe.order_server.order.infrastructure.client.hub.HubClient;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final HubClient hubClient;
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

    //    hubClient.getStockBySomething(); // TODO : 재고 확인에 따른 배송 가능 여부 파악
    UUID orderId = UUID.randomUUID();
    Order order =
        Order.ofCreate(
            orderId,
            deliveryClientAdapter.createDelivery(
                orderId, request.requestVendorId(), request.receiverVendorId()),
            productInfo,
            requestVendor,
            receiverVendor,
            request.requestNote());

    orderRepository.save(order);
  }

  @Override
  public OrderResponse readById(UUID orderId) {
    Order order = orderRepository.findById(orderId);
    return OrderResponse.from(order);
  }

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
        order -> order.getOrderId().toString()
        );
  }
}
