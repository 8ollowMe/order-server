package com.followMe.order_server.order.infrastructure.client.hub;

import com.followMe.order_server.order.domain.exception.HubClientUnavailableException;
import com.followMe.order_server.order.domain.exception.ProductNotFoundException;
import com.followMe.order_server.order.infrastructure.client.hub.dto.request.HubStockDecreaseRequest;
import com.followMe.order_server.order.infrastructure.client.hub.dto.request.HubStockRollbackRequest;
import com.followMe.order_server.order.infrastructure.client.hub.dto.response.HubStockDecreaseResponse;
import com.followMe.order_server.order.infrastructure.client.hub.dto.response.HubStockRollbackResponse;
import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;

public class HubFeignClientFallbackFactory implements FallbackFactory<HubFeignClient> {

  @Override
  public HubFeignClient create(Throwable cause) {

    return new HubFeignClient() {

      @Override
      public HubStockDecreaseResponse decreaseStockIfAvailable(HubStockDecreaseRequest request) {
        if (cause instanceof FeignException.NotFound) {
          throw new ProductNotFoundException();
        }
        throw new HubClientUnavailableException();
      }

      @Override
      public HubStockRollbackResponse rollbackStock(HubStockRollbackRequest request) {
        throw new HubClientUnavailableException();
      }
    };
  }
}
