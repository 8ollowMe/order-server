package com.followMe.order_server.order.application;

import com.followMe.order_server.order.application.dto.request.OrderCreateRequest;
import com.followMe.order_server.order.application.dto.response.OrderResponse;
import com.followMe.order_server.order.domain.Order;
import com.followMe.order_server.order.domain.ProductInfo;
import com.followMe.order_server.order.domain.VendorInfo;
import com.followMe.order_server.order.domain.repository.OrderRepository;
import com.followMe.order_server.order.infrastructure.client.delivery.DeliveryClientAdapter;
import com.followMe.order_server.order.infrastructure.client.hub.HubClient;
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
        new VendorInfo(request.requestVendorId(), request.requestVendorName());

    VendorInfo receiverVendor =
        new VendorInfo(request.receiverVendorId(), request.receiverVendorName());

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
}
