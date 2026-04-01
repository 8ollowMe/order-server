package com.followMe.order_server.order.infrastructure.client.hub.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProductStockResponse(UUID orderId, LocalDateTime completedAt) {}
