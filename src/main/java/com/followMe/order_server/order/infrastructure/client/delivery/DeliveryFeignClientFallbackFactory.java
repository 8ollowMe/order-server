package com.followMe.order_server.order.infrastructure.client.delivery;

import com.followMe.order_server.order.domain.exception.DeliveryClientUnavailableException;
import com.followMe.order_server.order.domain.exception.DeliveryNotFoundException;
import com.followMe.order_server.order.infrastructure.client.delivery.dto.request.DeliveryCreateRequest;
import com.followMe.order_server.order.infrastructure.client.delivery.dto.response.DeliveryCreateResponse;
import feign.FeignException;
import java.util.UUID;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class DeliveryFeignClientFallbackFactory implements FallbackFactory<DeliveryFeignClient> {

  @Override
  public DeliveryFeignClient create(Throwable cause) {
    return new DeliveryFeignClient() {
      public DeliveryCreateResponse createDelivery(DeliveryCreateRequest request) {
        throw new DeliveryClientUnavailableException();
      }

      public void cancelDelivery(UUID deliveryId) {
        if (cause instanceof FeignException.NotFound) {
          throw new DeliveryNotFoundException();
        }
        throw new DeliveryClientUnavailableException();
      }
    };
  }
}
