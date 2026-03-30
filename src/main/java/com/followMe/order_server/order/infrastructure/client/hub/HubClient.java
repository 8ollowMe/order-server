package com.followMe.order_server.order.infrastructure.client.hub;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "hub-server", fallbackFactory = HubClientFallbackFactory.class)
public interface HubClient {

  @GetMapping("/api/v1/stocks")
  Object getStockBySomething();
}
