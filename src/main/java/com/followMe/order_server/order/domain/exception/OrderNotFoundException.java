package com.followMe.order_server.order.domain.exception;

import com.followMe.common.exception.BusinessException;

public class OrderNotFoundException extends BusinessException {
  public OrderNotFoundException() {
    super(OrderErrorCode.ORDER_NOT_FOUND);
  }
}
