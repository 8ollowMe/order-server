package com.followMe.order_server.order.application;

import com.followMe.order_server.order.application.dto.request.OrderCreateRequest;
import com.followMe.order_server.order.application.dto.response.OrderResponse;
import java.util.UUID;

public interface OrderService {
  void create(OrderCreateRequest createRequest);

  OrderResponse readById(UUID orderId);
}
