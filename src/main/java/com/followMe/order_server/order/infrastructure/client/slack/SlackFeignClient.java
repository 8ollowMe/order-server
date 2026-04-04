package com.followMe.order_server.order.infrastructure.client.slack;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
    name = "message-server",
    fallbackFactory = SlackFeignClientFallbackFactory.class) // TODO is correct ?
public interface SlackFeignClient {

  @PostMapping("/internal/v1/slack/send")
  void sendSlack(SlackMessageSendRequest slackMessageSendRequest);
}
