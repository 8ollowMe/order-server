package com.followMe.order_server.order.infrastructure.client.hub.dto.response;

public record HubStockDecreaseResponse (
        boolean success,
        ProductStockResponse data,
        String error
){
}
