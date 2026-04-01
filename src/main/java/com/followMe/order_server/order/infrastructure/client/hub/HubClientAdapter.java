package com.followMe.order_server.order.infrastructure.client.hub;

import com.followMe.order_server.order.infrastructure.client.hub.dto.request.HubStockDecreaseRequest;
import com.followMe.order_server.order.infrastructure.client.hub.dto.request.HubStockRollbackRequest;
import com.followMe.order_server.order.infrastructure.client.hub.dto.request.ProductStockRequest;
import com.followMe.order_server.order.infrastructure.client.hub.dto.response.HubStockDecreaseResponse;
import com.followMe.order_server.order.infrastructure.client.hub.dto.response.HubStockRollbackResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubClientAdapter {

    private final HubFeignClient hubFeignClient;

    public HubStockDecreaseResponse decreaseStockIfAvailable(UUID orderId, UUID productId, int quantity) {
        ProductStockRequest productStockRequest = ProductStockRequest.of(productId, quantity);

        List<ProductStockRequest> products = new ArrayList<>();
        products.add(productStockRequest);
        HubStockDecreaseRequest decreaseRequest = HubStockDecreaseRequest.of(orderId, products);
        return hubFeignClient.decreaseStockIfAvailable(decreaseRequest);
    }

    public HubStockRollbackResponse rollbackStock(UUID orderId, UUID productId, int quantity){
        ProductStockRequest productStockRequest = ProductStockRequest.of(productId, quantity);

        List<ProductStockRequest> products = new ArrayList<>();
        products.add(productStockRequest);
        HubStockRollbackRequest rollbackRequest = HubStockRollbackRequest.of(orderId, products);
        return hubFeignClient.rollbackStock(rollbackRequest);
    }
}
