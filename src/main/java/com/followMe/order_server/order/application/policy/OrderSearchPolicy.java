package com.followMe.order_server.order.application.policy;

import com.followMe.order_server.order.application.dto.request.OrderSearchCondition;
import com.followMe.order_server.order.application.dto.request.UserContext;
import com.followMe.order_server.order.application.dto.request.UserRole;

public interface OrderSearchPolicy {

  boolean isSupport(UserRole userRole);

  OrderSearchCondition execute(OrderSearchCondition condition, UserContext userContext);
}
