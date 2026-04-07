package com.followMe.order_server.order.application.dto.request;

import java.util.UUID;

public record OrderCreateRequest(
    UUID productId,
    String productName,
    UUID resourceVendorId,
    String resourceVendorName,
    UUID resourceVendorHubId,
    String resourceVendorHubName,
    UUID receiverVendorId,
    String receiverVendorName,
    UUID receiverVendorHubId,
    String receiverVendorHubName,
    int quantity,
    String requestNote,
    UUID recipientId) {}
