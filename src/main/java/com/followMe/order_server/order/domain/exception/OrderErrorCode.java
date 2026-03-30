package com.followMe.order_server.order.domain.exception;

import com.followMe.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {
  DELIVERY_NOT_FOUND("D001", "배달을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  DELIVERY_CLIENT_UNAVAILABLE("D002", "배달 서비스를 일시적으로 사용할 수 없습니다.", HttpStatus.SERVICE_UNAVAILABLE);

  private final String code;
  private final String message;
  private final HttpStatus httpStatus;
}
