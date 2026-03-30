package com.followMe.order_server.order.domain.exception;

import com.followMe.common.exception.BusinessException;

public class DeliveryNotFoundException extends BusinessException {

  public DeliveryNotFoundException() {
    super(OrderErrorCode.DELIVERY_NOT_FOUND);
  }
}
