package com.followMe.order_server.order.infrastructure.client.hub;

import com.followMe.order_server.order.infrastructure.client.hub.dto.request.HubStockDecreaseRequest;
import com.followMe.order_server.order.infrastructure.client.hub.dto.request.HubStockRollbackRequest;
import com.followMe.order_server.order.infrastructure.client.hub.dto.response.HubStockDecreaseResponse;
import com.followMe.order_server.order.infrastructure.client.hub.dto.response.HubStockRollbackResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "hub-server", fallbackFactory = HubFeignClientFallbackFactory.class)
public interface HubFeignClient {

  @GetMapping("/internal/v1/stocks/order")
  HubStockDecreaseResponse decreaseStockIfAvailable(HubStockDecreaseRequest decreaseRequest);

  @GetMapping("/internal/v1/cancelstock")
  HubStockRollbackResponse rollbackStock(HubStockRollbackRequest rollbackRequest);
}
