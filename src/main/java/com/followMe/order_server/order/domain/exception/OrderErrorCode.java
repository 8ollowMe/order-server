package com.followMe.order_server.order.domain.exception;

import com.followMe.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {

  // ===================== ORDER =====================
  ORDER_NOT_FOUND("O001", "주문을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

  INVALID_ORDER_QUANTITY("O002", "주문 수량은 1 이상이어야 합니다.", HttpStatus.BAD_REQUEST),

  ORDER_STATE_TRANSITION_NOT_ALLOWED("O003", "해당 상태로 변경할 수 없습니다.", HttpStatus.CONFLICT),

  ORDER_CANCELLATION_NOT_ALLOWED("O004", "현재 상태에서는 주문을 취소할 수 없습니다.", HttpStatus.CONFLICT),

  // ===================== PRODUCT =====================
  PRODUCT_NOT_FOUND("P001", "상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

  STOCK_SHORTAGE("P002", "재고가 부족합니다.", HttpStatus.CONFLICT),

  // ===================== DELIVERY =====================
  DELIVERY_NOT_FOUND("D001", "배달 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

  DELIVERY_SERVICE_UNAVAILABLE("D002", "배달 서비스를 사용할 수 없습니다.", HttpStatus.SERVICE_UNAVAILABLE),

  // ===================== HUB =====================
  HUB_SERVICE_UNAVAILABLE("H001", "허브 서비스를 사용할 수 없습니다.", HttpStatus.SERVICE_UNAVAILABLE),

  // ===================== SLACK =====================
  SLACK_SEVER_NOT_AVAILABLE("S001", "슬랙 메세지 전송 서비스를 사용할 수 없습니다", HttpStatus.SERVICE_UNAVAILABLE);

  private final String code;
  private final String message;
  private final HttpStatus httpStatus;
}
