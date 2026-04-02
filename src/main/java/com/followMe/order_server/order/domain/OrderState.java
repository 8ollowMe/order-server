package com.followMe.order_server.order.domain;

public enum OrderState {
  CREATED,
  COMPLETED,
  CANCELLED;

  public boolean canTransitionTo(OrderState target) {
    return switch (this) {
      case CREATED -> target == COMPLETED || target == CANCELLED;
      case COMPLETED, CANCELLED -> false;
    };
  }
}
