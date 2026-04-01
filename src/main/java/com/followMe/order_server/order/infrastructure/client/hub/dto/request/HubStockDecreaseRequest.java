package com.followMe.order_server.order.infrastructure.client.hub.dto.request;

import java.util.List;
import java.util.UUID;

public record HubStockDecreaseRequest(UUID orderId, List<ProductStockRequest> products) {
  public static HubStockDecreaseRequest of(UUID orderId, List<ProductStockRequest> products) {
    return new HubStockDecreaseRequest(orderId, products);
  }
}
