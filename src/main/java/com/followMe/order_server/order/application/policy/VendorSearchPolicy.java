package com.followMe.order_server.order.application.policy;

import com.followMe.order_server.order.application.dto.request.OrderSearchCondition;
import com.followMe.order_server.order.application.dto.request.UserContext;
import com.followMe.order_server.order.application.dto.request.UserRole;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class VendorSearchPolicy implements OrderSearchPolicy {

  @Override
  public boolean isSupport(UserRole userRole) {
    return userRole == UserRole.VENDOR;
  }

  @Override
  public OrderSearchCondition execute(OrderSearchCondition condition, UserContext userContext) {
    UUID vendorId = userContext.vendorId();
    return new OrderSearchCondition(
        null,
        condition.productId(),
        condition.orderId(),
        condition.deliveryManagerId(),
        condition.productName(),
        condition.startDateTime(),
        condition.endDateTime(),
        condition.createdBy(),
        vendorId,
        condition.status());
  }
}
