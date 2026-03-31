package com.followMe.order_server.order.application.dto.request;

import com.followMe.order_server.order.domain.OrderState;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderSearchCondition(
    UUID hubId,
    UUID productId,
    UUID orderId,
    String productName,
    LocalDateTime startDateTime,
    LocalDateTime endDateTime,
    UUID createdBy,
    UUID vendorId,
    OrderState status) {}
