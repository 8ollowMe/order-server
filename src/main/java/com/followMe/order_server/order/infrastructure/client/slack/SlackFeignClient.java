package com.followMe.order_server.order.infrastructure.client.slack;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@CircuitBreaker(name = "message-server")
@FeignClient(
    name = "message-server",
    fallbackFactory = SlackFeignClientFallbackFactory.class) // TODO is correct ?
public interface SlackFeignClient {

  @PostMapping("/internal/v1/slack/send")
  void sendSlack(SlackMessageSendRequest slackMessageSendRequest);
}
