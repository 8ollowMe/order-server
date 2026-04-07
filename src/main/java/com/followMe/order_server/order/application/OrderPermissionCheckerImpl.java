package com.followMe.order_server.order.application;

import com.followMe.common.exception.BusinessException;
import com.followMe.common.exception.CommonErrorCode;
import com.followMe.order_server.order.application.dto.request.UserContext;
import com.followMe.order_server.order.domain.Order;
import com.followMe.order_server.order.domain.service.OrderPermissionChecker;
import org.springframework.stereotype.Component;

@Component
public class OrderPermissionCheckerImpl implements OrderPermissionChecker {

  @Override
  public void checkDeletePermission(UserContext userContext, Order order) {
    checkUpdate(userContext, order);
  }

  @Override
  public void checkUpdatePermission(UserContext userContext, Order order) {
    checkUpdate(userContext, order);
  }

  @Override
  public void checkCancelPermission(UserContext userContext, Order order) {
    checkUpdate(userContext, order);
  }

  private void checkUpdate(UserContext userContext, Order order) {
    switch (userContext.userRole()) {
      case VENDOR, DELIVERY_MANAGER -> {
        throw new BusinessException(CommonErrorCode.FORBIDDEN);
      }
      case HUB_MANAGER -> {
        if (userContext.hubId() == order.getReceiverVendor().getHubId()
            || userContext.hubId() == order.getResourceVendor().getHubId()) {

        } else {
          throw new BusinessException((CommonErrorCode.FORBIDDEN));
        }
      }
      case MASTER -> {}
    }
  }
}
