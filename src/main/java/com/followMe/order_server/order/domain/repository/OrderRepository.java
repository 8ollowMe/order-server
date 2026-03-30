package com.followMe.order_server.order.domain.repository;

import com.followMe.order_server.order.domain.Order;
import java.util.UUID;

public interface OrderRepository {

  void save(Order order);

  Order findById(UUID orderId);
}
