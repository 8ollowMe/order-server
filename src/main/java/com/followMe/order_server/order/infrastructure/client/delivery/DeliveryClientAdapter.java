package com.followMe.order_server.order.infrastructure.client.delivery;

import com.followMe.order_server.order.infrastructure.client.delivery.dto.request.DeliveryCreateRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryClientAdapter {

  private final DeliveryFeignClient deliveryFeignClient;

  public UUID createDelivery(UUID orderId, UUID sourceHubId, UUID vendorId) {
    DeliveryCreateRequest createRequest = DeliveryCreateRequest.of(orderId, sourceHubId, vendorId);
    return deliveryFeignClient.createDelivery(createRequest).deliveryId();
  }
}
