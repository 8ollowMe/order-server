package com.followMe.order_server.order.domain.exception;

import com.followMe.common.exception.BusinessException;

public class ProductNotFoundException extends BusinessException {
  public ProductNotFoundException() {
    super(OrderErrorCode.PRODUCT_NOT_FOUND);
  }
}
