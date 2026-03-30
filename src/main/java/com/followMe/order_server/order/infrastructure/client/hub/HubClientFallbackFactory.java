package com.followMe.order_server.order.infrastructure.client.hub;

import org.springframework.cloud.openfeign.FallbackFactory;

public class HubClientFallbackFactory implements FallbackFactory<HubClient> {
  @Override
  public HubClient create(Throwable cause) {
    return null;
  }
}
