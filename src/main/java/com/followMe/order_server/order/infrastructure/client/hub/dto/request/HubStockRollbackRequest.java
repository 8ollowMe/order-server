package com.followMe.order_server.order.infrastructure.client.hub.dto.request;

import java.util.List;
import java.util.UUID;

public record HubStockRollbackRequest(UUID orderId, List<ProductStockRequest> products) {
  public static HubStockRollbackRequest of(UUID orderId, List<ProductStockRequest> products) {
    return new HubStockRollbackRequest(orderId, products);
  }
}
