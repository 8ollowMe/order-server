package com.followMe.order_server.order.domain.exception;

import com.followMe.common.exception.BusinessException;

public class HubClientUnavailableException extends BusinessException {
  public HubClientUnavailableException() {
    super(OrderErrorCode.HUB_SERVICE_UNAVAILABLE);
  }
}
