package com.followMe.order_server.order.domain.exception;

import com.followMe.common.exception.BusinessException;

public class InvalidStatusToCancelException extends BusinessException {
  public InvalidStatusToCancelException() {
    super(OrderErrorCode.ORDER_CANCELLATION_NOT_ALLOWED);
  }
}
