package com.followMe.order_server.order.infrastructure.client.hub;

import com.followMe.order_server.order.config.FeignConfig;
import com.followMe.order_server.order.config.FeignOkHttpConfiguration;
import com.followMe.order_server.order.infrastructure.client.hub.dto.request.HubStockDecreaseRequest;
import com.followMe.order_server.order.infrastructure.client.hub.dto.request.HubStockRollbackRequest;
import com.followMe.order_server.order.infrastructure.client.hub.dto.response.HubStockDecreaseResponse;
import com.followMe.order_server.order.infrastructure.client.hub.dto.response.HubStockRollbackResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "hub-server",
    configuration = {FeignOkHttpConfiguration.class, FeignConfig.class},
    fallbackFactory = HubFeignClientFallbackFactory.class)
public interface HubFeignClient {

  @PatchMapping("/internal/v1/stocks/order")
  HubStockDecreaseResponse decreaseStockIfAvailable(
      @RequestBody HubStockDecreaseRequest decreaseRequest);

  @PatchMapping("/internal/v1/cancel/stock")
  HubStockRollbackResponse rollbackStock(@RequestBody HubStockRollbackRequest rollbackRequest);
}
