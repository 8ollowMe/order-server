package com.followMe.order_server.order.infrastructure.client.slack;

import com.followMe.order_server.order.domain.exception.SlackClientUnavailableException;
import org.springframework.cloud.openfeign.FallbackFactory;

public class SlackFeignClientFallbackFactory implements FallbackFactory<SlackFeignClient> {

  @Override
  public SlackFeignClient create(Throwable cause) {

    return new SlackFeignClient() {

      @Override
      public void sendSlack(SlackMessageSendRequest slackMessageSendRequest) {
        throw new SlackClientUnavailableException();
      }
    };
  }
}
