package com.followMe.order_server.order.infrastructure.client.slack;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SlackClientAdapter {

  private final SlackFeignClient slackFeignClient;

  public void sendSlack(UUID userId, UUID orderId, String message) {
    SlackMessageSendRequest slackMessageSendRequest =
        SlackMessageSendRequest.of(userId, orderId, message);
    slackFeignClient.sendSlack(slackMessageSendRequest);
  }
}
