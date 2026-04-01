package com.followMe.order_server.order.application.dto.request;

import java.util.UUID;

public record UserContext(UUID userId, UserRole userRole, UUID hubId, UUID vendorId) {}
