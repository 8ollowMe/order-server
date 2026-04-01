package com.followMe.order_server.order.infrastructure.client.hub;

import org.springframework.cloud.openfeign.FallbackFactory;

public class HubFeignClientFallbackFactory implements FallbackFactory<HubFeignClient> {
  @Override
  public HubFeignClient create(Throwable cause) {
    throw new RuntimeException("허브 서버 API 연결 불가");
  }
}
