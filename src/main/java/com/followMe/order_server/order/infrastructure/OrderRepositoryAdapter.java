package com.followMe.order_server.order.infrastructure;

import com.followMe.order_server.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
}
