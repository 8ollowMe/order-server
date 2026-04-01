package com.followMe.order_server.order.application.dto.request;

import java.util.UUID;

public record OrderCreateRequest(
    UUID productId,
    String productName,
    UUID requestVendorId,
    String requestVendorName,
    UUID requestVendorHubId,
    UUID receiverVendorId,
    String receiverVendorName,
    UUID receiverVendorHubId,
    int quantity,
    String requestNote) {}
