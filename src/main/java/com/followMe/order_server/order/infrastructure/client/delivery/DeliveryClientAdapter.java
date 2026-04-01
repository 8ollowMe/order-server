package com.followMe.order_server.order.infrastructure.client.delivery;

import com.followMe.order_server.order.infrastructure.client.delivery.dto.request.DeliveryCreateRequest;
import com.followMe.order_server.order.infrastructure.client.delivery.dto.response.DeliveryCreateResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryClientAdapter {

  private final DeliveryFeignClient deliveryFeignClient;

  public DeliveryCreateResponse createDelivery(
      UUID orderId, UUID sourceHubId, UUID receiverVendorId) {
    DeliveryCreateRequest createRequest =
        DeliveryCreateRequest.of(orderId, sourceHubId, receiverVendorId);
    return deliveryFeignClient.createDelivery(createRequest);
  }
}
