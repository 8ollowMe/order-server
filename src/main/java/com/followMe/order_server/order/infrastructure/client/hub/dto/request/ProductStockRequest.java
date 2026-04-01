package com.followMe.order_server.order.infrastructure.client.hub.dto.request;

import java.util.UUID;

public record ProductStockRequest(
        UUID id,
        int quantity
) {
    public static ProductStockRequest of(UUID productId, int quantity) {
        return new ProductStockRequest(productId, quantity);
    }
}
