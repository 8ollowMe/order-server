package com.followMe.order_server.order.domain.repository;

import com.followMe.order_server.order.application.dto.request.OrderSearchCondition;
import com.followMe.order_server.order.domain.Order;
import java.util.List;
import java.util.UUID;

public interface OrderRepository {

  void save(Order order);

  Order findById(UUID orderId);

  List<Order> searchByCursor(String cursor, int size, OrderSearchCondition condition);
}
