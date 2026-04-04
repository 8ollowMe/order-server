package com.followMe.order_server.order.presentation;

import com.followMe.common.pagination.CursorRequest;
import com.followMe.common.pagination.CursorResponse;
import com.followMe.common.response.ApiResponse;
import com.followMe.order_server.order.application.OrderService;
import com.followMe.order_server.order.application.dto.request.OrderCreateRequest;
import com.followMe.order_server.order.application.dto.request.OrderSearchCondition;
import com.followMe.order_server.order.application.dto.request.OrderUpdateRequest;
import com.followMe.order_server.order.application.dto.request.OrderUpdateStateRequest;
import com.followMe.order_server.order.application.dto.request.UserContext;
import com.followMe.order_server.order.application.dto.request.UserRole;
import com.followMe.order_server.order.application.dto.response.OrderResponse;
import com.followMe.order_server.order.infrastructure.client.repository.OrderRepositoryAdapter;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

  private final OrderService orderService;
  private final OrderRepositoryAdapter orderRepositoryAdapter;

  @ModelAttribute
  public UserContext userContext(
      @RequestHeader("X-User-Id") UUID userId,
      @RequestHeader("X-User-Role") UserRole role,
      @RequestHeader(value = "X-Hub-Id", required = false) UUID hubId,
      @RequestHeader(value = "X-Vendor-Id", required = false) UUID vendorId) {
    return new UserContext(userId, role, hubId, vendorId);
  }

  @PostMapping
  public ResponseEntity<ApiResponse> create(@RequestBody OrderCreateRequest createRequest) {
    orderService.create(createRequest);
    return ApiResponse.created();
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<ApiResponse> readById(@PathVariable UUID orderId) {
    OrderResponse response = orderService.readById(orderId);
    return ApiResponse.ok(response);
  }

  @GetMapping
  public ResponseEntity<ApiResponse> search(
      CursorRequest cursorRequest,
      @ModelAttribute OrderSearchCondition condition,
      @ModelAttribute UserContext userContext) {
    // TODO: 인증/인가에 따라 UserContext 변경 가능
    CursorResponse<OrderResponse> response =
        orderService.search(cursorRequest, condition, userContext);
    return ApiResponse.ok(response);
  }

  @PatchMapping("/{orderId}")
  public ResponseEntity<ApiResponse> update(
      @PathVariable UUID orderId, @RequestBody OrderUpdateRequest updateRequest) {
    // TODO: 인증인가 필요
    orderService.update(orderId, updateRequest);
    return ApiResponse.ok();
  }

  @PatchMapping("/{orderId}/state")
  public ResponseEntity<ApiResponse> updateStatus(
      @PathVariable UUID orderId, @RequestBody OrderUpdateStateRequest updateStateRequest) {
    orderService.updateStatus(orderId, updateStateRequest);
    return ApiResponse.ok();
  }

  @DeleteMapping("/{orderId}")
  public ResponseEntity<ApiResponse> delete(@PathVariable UUID orderId) {
    // TODO: 인증인가 필요
    orderService.softDeleteById(orderId);
    return ApiResponse.ok();
  }

  @PatchMapping("/{orderId}/cancel")
  public ResponseEntity<ApiResponse> cancel(
      @PathVariable UUID orderId, @ModelAttribute UserContext userContext) {
    // TODO: 인증인가 필요
    orderService.cancel(orderId, userContext.userId());
    return ApiResponse.ok();
  }
}
