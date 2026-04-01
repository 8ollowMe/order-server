package com.followMe.order_server.order.application;

import com.followMe.order_server.order.application.dto.request.OrderSearchCondition;
import com.followMe.order_server.order.application.dto.request.UserContext;
import com.followMe.order_server.order.application.dto.request.UserRole;
import java.util.UUID;

public class OrderSearchFilter {

  public static OrderSearchCondition applyRoleFilter(
      OrderSearchCondition condition, UserContext userContext) {
    UserRole role = userContext.userRole();

    return switch (role) {
      case VENDOR -> {
        UUID vendorId = userContext.vendorId();
        yield new OrderSearchCondition(
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
      case DELIVERY_MANAGER -> {
        UUID deliveryManagerId = userContext.userId();
        yield new OrderSearchCondition(
            condition.hubId(),
            condition.productId(),
            condition.orderId(),
            deliveryManagerId,
            condition.productName(),
            condition.startDateTime(),
            condition.endDateTime(),
            condition.createdBy(),
            condition.vendorId(),
            condition.status());
      }
      case HUB_MANAGER ->
          new OrderSearchCondition(
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
      case MASTER -> condition;
    };
  }
}
