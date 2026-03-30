package com.followMe.order_server.order.infrastructure.client.delivery;

import com.followMe.order_server.order.domain.exception.DeliveryClientUnavailableException;
import com.followMe.order_server.order.domain.exception.DeliveryNotFoundException;
import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class DeliveryFeignClientFallbackFactory implements FallbackFactory<DeliveryFeignClient> {

  @Override
  public DeliveryFeignClient create(Throwable cause) {
    return request -> {
      if (cause instanceof FeignException.NotFound) {
        throw new DeliveryNotFoundException();
      }
      throw new DeliveryClientUnavailableException();
    };
  }
}
