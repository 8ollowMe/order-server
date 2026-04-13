package com.followMe.order_server.order.application.policy;

import com.followMe.order_server.order.application.dto.request.OrderSearchCondition;
import com.followMe.order_server.order.application.dto.request.UserContext;
import com.followMe.order_server.order.application.dto.request.UserRole;
import org.springframework.stereotype.Component;

@Component
public class DeliverySearchPolicy implements OrderSearchPolicy {
  @Override
  public boolean isSupport(UserRole userRole) {
    return userRole == UserRole.DELIVERY_MANAGER;
  }

  @Override
  public OrderSearchCondition execute(OrderSearchCondition condition, UserContext userContext) {
    return new OrderSearchCondition(
        userContext.hubId(),
        condition.productId(),
        condition.orderId(),
        condition.deliveryManagerId(),
        condition.productName(),
        condition.startDateTime(),
        condition.endDateTime(),
        condition.createdBy(),
        condition.vendorId(),
        condition.status());
  }
}
