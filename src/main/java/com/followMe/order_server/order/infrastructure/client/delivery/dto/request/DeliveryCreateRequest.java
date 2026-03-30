package com.followMe.order_server.order.infrastructure.client.delivery.dto.request;

import java.util.UUID;

public record DeliveryCreateRequest(UUID orderId, UUID sourceHubId, UUID vendorId) {
  public static DeliveryCreateRequest of(UUID orderId, UUID sourceHubId, UUID vendorId) {
    return new DeliveryCreateRequest(orderId, sourceHubId, vendorId);
  }
}
