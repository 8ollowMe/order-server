package com.followMe.order_server.order.infrastructure.client.slack;

import java.util.UUID;

public record SlackMessageSendRequest(
    String messageType, UUID userId, String referenceType, UUID referenceId, String message) {
  public static SlackMessageSendRequest of(UUID userId, UUID orderId, String message) {
    return new SlackMessageSendRequest("ORDER", userId, "referenceType", orderId, message);
  }
}
