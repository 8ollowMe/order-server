package com.followMe.order_server.order.application.dto.response;

import com.followMe.order_server.order.domain.Order;
import com.followMe.order_server.order.domain.OrderState;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderResponse(
    UUID orderId,
    UUID deliveryId,
    UUID productid,
    String productName,
    int quantity,
    UUID requestVendorId,
    String requestVendorName,
    UUID receiverVendorId,
    String receiverVendorName,
    String requestNote,
    OrderState status,
    LocalDateTime cancelledAt,
    UUID cancelledBy,
    UUID recipientId) {
  public static OrderResponse from(Order order) {
    return new OrderResponse(
        order.getOrderId(),
        order.getDeliveryId(),
        order.getProductInfo().getProductId(),
        order.getProductInfo().getProductName(),
        order.getProductInfo().getQuantity(),
        order.getResourceVendor().getVendorId(),
        order.getResourceVendor().getVendorName(),
        order.getReceiverVendor().getVendorId(),
        order.getReceiverVendor().getVendorName(),
        order.getRequestNote(),
        order.getStatus(),
        order.getCancelledAt(),
        order.getCancelledBy(),
        order.getRecipientId());
  }
}
