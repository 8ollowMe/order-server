package com.followMe.order_server.order.infrastructure.client.delivery.dto.response;

import java.util.UUID;

public record DeliveryCreateResponse(UUID deliveryId, UUID deliveryManagerId) {}
