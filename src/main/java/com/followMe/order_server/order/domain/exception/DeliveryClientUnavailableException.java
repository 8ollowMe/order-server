package com.followMe.order_server.order.domain.exception;

import com.followMe.common.exception.BusinessException;

public class DeliveryClientUnavailableException extends BusinessException {

  public DeliveryClientUnavailableException() {
    super(OrderErrorCode.DELIVERY_SERVICE_UNAVAILABLE);
  }
}
