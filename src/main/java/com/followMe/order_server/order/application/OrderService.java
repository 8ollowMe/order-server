package com.followMe.order_server.order.application;

import com.followMe.order_server.order.application.dto.request.OrderCreateRequest;

public interface OrderService {
  void create(OrderCreateRequest createRequest);
}
