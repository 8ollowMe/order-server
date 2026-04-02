package com.followMe.order_server.order.domain.exception;

import com.followMe.common.exception.BusinessException;

public class InvalidOrderQuantityException extends BusinessException {
  public InvalidOrderQuantityException() {
    super(OrderErrorCode.INVALID_ORDER_QUANTITY);
  }
}
