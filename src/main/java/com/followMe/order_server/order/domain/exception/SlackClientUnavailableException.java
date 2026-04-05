package com.followMe.order_server.order.domain.exception;

import com.followMe.common.exception.BusinessException;

public class SlackClientUnavailableException extends BusinessException {
  public SlackClientUnavailableException() {
    super(OrderErrorCode.SLACK_SEVER_NOT_AVAILABLE);
  }
}
