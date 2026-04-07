package com.followMe.order_server.order.infrastructure.repository;

import com.followMe.order_server.order.domain.Order;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, UUID> {}
