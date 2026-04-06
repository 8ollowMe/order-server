package com.followMe.order_server.order.infrastructure.client.delivery;

import com.followMe.order_server.order.config.FeignConfig;
import com.followMe.order_server.order.config.FeignOkHttpConfiguration;
import com.followMe.order_server.order.infrastructure.client.delivery.dto.request.DeliveryCreateRequest;
import com.followMe.order_server.order.infrastructure.client.delivery.dto.response.DeliveryCreateResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "delivery-server",
    configuration = {FeignOkHttpConfiguration.class, FeignConfig.class},
    fallbackFactory = DeliveryFeignClientFallbackFactory.class)
public interface DeliveryFeignClient {

  @PostMapping("/internal/v1/deliveries")
  DeliveryCreateResponse createDelivery(@RequestBody DeliveryCreateRequest request);

  @PatchMapping("/internal/v1/deliveries/{deliveryId}/cancel")
  void cancelDelivery(@PathVariable UUID deliveryId);
}
