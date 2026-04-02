package com.followMe.order_server.order.domain.exception;

import com.followMe.common.exception.BusinessException;

public class StockShortageException extends BusinessException {
  public StockShortageException() {
    super(OrderErrorCode.STOCK_SHORTAGE);
  }
}
