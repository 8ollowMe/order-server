package com.followMe.order_server.order.infrastructure.client.delivery.dto.request;

import java.util.UUID;

public record DeliveryCreateRequest(
    UUID orderId, UUID sourceHubId, UUID vendorId, UUID recipientId) {
  public static DeliveryCreateRequest of(
      UUID orderId, UUID sourceHubId, UUID vendorId, UUID recipientId) {
    return new DeliveryCreateRequest(orderId, sourceHubId, vendorId, recipientId);
  }
}
