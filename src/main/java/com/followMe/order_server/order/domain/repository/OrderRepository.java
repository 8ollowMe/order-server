package com.followMe.order_server.order.domain.repository;

import com.followMe.order_server.order.domain.Order;

public interface OrderRepository {

  void save(Order order);
}
