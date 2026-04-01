package com.followMe.order_server.order.infrastructure.client.repository;

import com.followMe.order_server.order.application.dto.request.OrderSearchCondition;
import com.followMe.order_server.order.domain.Order;
import com.followMe.order_server.order.domain.OrderState;
import com.followMe.order_server.order.domain.QOrder;
import com.followMe.order_server.order.domain.exception.OrderNotFoundException;
import com.followMe.order_server.order.domain.repository.OrderRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {

  private final OrderJpaRepository orderJpaRepository;
  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public void save(Order order) {
    orderJpaRepository.save(order);
  }

  @Override
  public Order findById(UUID orderId) {
    return orderJpaRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
  }

  public List<Order> searchByCursor(String cursor, int size, OrderSearchCondition condition) {

    QOrder order = QOrder.order;

    return jpaQueryFactory
        .selectFrom(order)
        .where(
            hubEq(condition.hubId()),
            deliveryManagerEa(condition.deliveryManagerId()),
            productIdEq(condition.productId()),
            orderIdEq(condition.orderId()),
            productNameContains(condition.productName()),
            createdAtGoe(condition.startDateTime()),
            createdAtLoe(condition.endDateTime()),
            createdByEq(condition.createdBy()),
            vendorEq(condition.vendorId()),
            statusEq(condition.status()))
        .orderBy(order.createdAt.desc(), order.orderId.desc()) // 안정적인 커서 페이징
        .limit(size)
        .fetch();
  }

  private BooleanExpression hubEq(UUID hubId) {
    if (hubId == null) return null;

    QOrder order = QOrder.order;
    return order.requestVendor.hubId.eq(hubId).or(order.receiverVendor.hubId.eq(hubId));
  }

  private BooleanExpression deliveryManagerEa(UUID deliveryManagerId) {
    if (deliveryManagerId == null) return null;
    return QOrder.order.currentDeliveryManagerId.eq(deliveryManagerId);
  }

  private BooleanExpression productIdEq(UUID productId) {
    if (productId == null) return null;
    return QOrder.order.productInfo.productId.eq(productId);
  }

  private BooleanExpression orderIdEq(UUID orderId) {
    if (orderId == null) return null;
    return QOrder.order.orderId.eq(orderId);
  }

  private BooleanExpression productNameContains(String productName) {
    if (productName == null || productName.isBlank()) return null;
    return QOrder.order.productInfo.productName.contains(productName);
  }

  private BooleanExpression createdAtGoe(LocalDateTime start) {
    if (start == null) return null;
    return QOrder.order.createdAt.goe(Instant.from(start));
  }

  private BooleanExpression createdAtLoe(LocalDateTime end) {
    if (end == null) return null;
    return QOrder.order.createdAt.loe(Instant.from(end));
  }

  private BooleanExpression createdByEq(UUID createdBy) {
    if (createdBy == null) return null;
    return QOrder.order.createdBy.eq(createdBy);
  }

  private BooleanExpression vendorEq(UUID vendorId) {
    if (vendorId == null) return null;

    QOrder order = QOrder.order;
    return order.requestVendor.vendorId.eq(vendorId).or(order.receiverVendor.vendorId.eq(vendorId));
  }

  private BooleanExpression statusEq(OrderState status) {
    if (status == null) return null;
    return QOrder.order.status.eq(status);
  }
}
