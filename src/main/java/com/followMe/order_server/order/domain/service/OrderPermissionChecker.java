package com.followMe.order_server.order.domain.service;

import com.followMe.order_server.order.application.dto.request.UserContext;
import com.followMe.order_server.order.domain.Order;

public interface OrderPermissionChecker {

  void checkDeletePermission(UserContext userContext, Order order);

  void checkUpdatePermission(UserContext userContext, Order order);

  void checkCancelPermission(UserContext userContext, Order order);
}
