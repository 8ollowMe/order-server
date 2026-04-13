package com.followMe.order_server.order.application.policy;

import com.followMe.order_server.order.application.dto.request.OrderSearchCondition;
import com.followMe.order_server.order.application.dto.request.UserContext;
import com.followMe.order_server.order.application.dto.request.UserRole;
import org.springframework.stereotype.Component;

@Component
public class MasterSearchPolicy implements OrderSearchPolicy {
  @Override
  public boolean isSupport(UserRole userRole) {
    return userRole == UserRole.MASTER;
  }

  @Override
  public OrderSearchCondition execute(OrderSearchCondition condition, UserContext userContext) {
    return condition;
  }
}
