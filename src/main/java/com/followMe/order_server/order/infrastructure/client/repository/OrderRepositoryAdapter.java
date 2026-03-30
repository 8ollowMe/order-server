package com.followMe.order_server.order.infrastructure.client.repository;

import com.followMe.order_server.order.domain.Order;
import com.followMe.order_server.order.domain.exception.OrderNotFoundException;
import com.followMe.order_server.order.domain.repository.OrderRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {

  private final OrderJpaRepository orderJpaRepository;

  @Override
  public void save(Order order) {
    orderJpaRepository.save(order);
  }

  @Override
  public Order findById(UUID orderId) {
    return orderJpaRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
  }
}
