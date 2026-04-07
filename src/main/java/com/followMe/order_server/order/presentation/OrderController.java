package com.followMe.order_server.order.presentation;

import com.followMe.common.pagination.CursorRequest;
import com.followMe.common.pagination.CursorResponse;
import com.followMe.common.response.ApiResponse;
import com.followMe.order_server.order.application.OrderService;
import com.followMe.order_server.order.application.dto.request.*;
import com.followMe.order_server.order.application.dto.response.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(name = "Order", description = "주문 관련 API")
public class OrderController {

  private final OrderService orderService;

  @ModelAttribute
  public UserContext userContext(
      @RequestHeader("X-User-Id") UUID userId,
      @RequestHeader("X-Role") String userRole,
      @RequestHeader(value = "X-Hub-Id", required = false) UUID hubId,
      @RequestHeader(value = "X-Vendor-Id", required = false) UUID vendorId) {
    UserRole role = UserRole.valueOf(userRole);
    return new UserContext(userId, role, hubId, vendorId);
  }

  @Operation(summary = "주문 생성", description = "새로운 주문을 생성합니다.")
  @PostMapping
  public ResponseEntity<ApiResponse> create(@RequestBody OrderCreateRequest createRequest) {
    orderService.create(createRequest);
    return ApiResponse.created();
  }

  @Operation(summary = "주문 조회", description = "주문 ID로 주문 정보를 조회합니다.")
  @GetMapping("/{orderId}")
  public ResponseEntity<ApiResponse> readById(
      @Parameter(description = "조회할 주문 ID") @PathVariable UUID orderId) {
    OrderResponse response = orderService.readById(orderId);
    return ApiResponse.ok(response);
  }

  @Operation(summary = "주문 검색", description = "조건에 맞는 주문 목록을 조회합니다.")
  @GetMapping
  public ResponseEntity<ApiResponse> search(
      CursorRequest cursorRequest,
      @ModelAttribute OrderSearchCondition condition,
      @ModelAttribute UserContext userContext) {
    CursorResponse<OrderResponse> response =
        orderService.search(cursorRequest, condition, userContext);
    return ApiResponse.ok(response);
  }

  @Operation(summary = "주문 업데이트", description = "주문 정보를 수정합니다.")
  @PatchMapping("/{orderId}")
  public ResponseEntity<ApiResponse> update(
      @Parameter(description = "수정할 주문 ID") @PathVariable UUID orderId,
      @RequestBody OrderUpdateRequest updateRequest,
      @ModelAttribute UserContext userContext) {
    orderService.update(orderId, updateRequest, userContext);
    return ApiResponse.ok();
  }

  @Operation(summary = "주문 상태 변경", description = "주문 상태를 변경합니다.")
  @PatchMapping("/{orderId}/state")
  public ResponseEntity<ApiResponse> updateStatus(
      @Parameter(description = "상태를 변경할 주문 ID") @PathVariable UUID orderId,
      @RequestBody OrderUpdateStateRequest updateStateRequest) {
    orderService.updateStatus(orderId, updateStateRequest);
    return ApiResponse.ok();
  }

  @Operation(summary = "주문 삭제(Soft Delete)", description = "주문을 소프트 삭제합니다.")
  @DeleteMapping("/{orderId}")
  public ResponseEntity<ApiResponse> delete(
      @Parameter(description = "삭제할 주문 ID") @PathVariable UUID orderId,
      @ModelAttribute UserContext userContext) {
    orderService.softDeleteById(orderId, userContext);
    return ApiResponse.ok();
  }

  @Operation(summary = "주문 취소", description = "주문을 취소합니다.")
  @PatchMapping("/{orderId}/cancel")
  public ResponseEntity<ApiResponse> cancel(
      @Parameter(description = "취소할 주문 ID") @PathVariable UUID orderId,
      @ModelAttribute UserContext userContext) {
    orderService.cancel(orderId, userContext);
    return ApiResponse.ok();
  }
}
