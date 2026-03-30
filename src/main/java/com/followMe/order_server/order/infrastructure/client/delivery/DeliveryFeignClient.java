package com.followMe.order_server.order.infrastructure.client.delivery;

import com.followMe.order_server.order.infrastructure.client.delivery.dto.request.DeliveryCreateRequest;
import com.followMe.order_server.order.infrastructure.client.delivery.dto.response.DeliveryCreateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "delivery-service")
public interface DeliveryFeignClient {

  @PostMapping("/deliveries")
  DeliveryCreateResponse createDelivery(@RequestBody DeliveryCreateRequest request);
}
