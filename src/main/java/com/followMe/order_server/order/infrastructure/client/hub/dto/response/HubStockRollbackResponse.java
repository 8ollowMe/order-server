package com.followMe.order_server.order.infrastructure.client.hub.dto.response;

public record HubStockRollbackResponse(
        boolean success,
        ProductStockResponse data,
        String error
){
}
