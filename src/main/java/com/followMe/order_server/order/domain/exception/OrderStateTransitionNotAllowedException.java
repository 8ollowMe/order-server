package com.followMe.order_server.order.domain.exception;

import com.followMe.common.exception.BusinessException;

public class OrderStateTransitionNotAllowedException extends BusinessException {
  public OrderStateTransitionNotAllowedException() {
    super(OrderErrorCode.ORDER_STATE_TRANSITION_NOT_ALLOWED);
  }
}
