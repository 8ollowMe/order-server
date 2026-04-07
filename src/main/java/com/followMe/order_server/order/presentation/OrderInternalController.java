package com.followMe.order_server.order.presentation;

import com.followMe.common.response.ApiResponse;
import com.followMe.order_server.order.application.OrderService;
import com.followMe.order_server.order.application.dto.request.OrderUpdateDeliveryManagerRequest;
import com.followMe.order_server.order.application.dto.request.OrderUpdateStateRequest;
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
@RequestMapping("/internal/v1/orders")
@Tag(name = "Internal Order", description = "내부 주문 관련 API")
public class OrderInternalController {

  private final OrderService orderService;

  @Operation(summary = "주문 조회(내부용)", description = "주문 ID로 주문 정보를 조회합니다. 내부 API 전용")
  @GetMapping("/{orderId}")
  public OrderResponse readById(@Parameter(description = "조회할 주문 ID") @PathVariable UUID orderId) {
    return orderService.readById(orderId);
  }

  @Operation(summary = "주문 상태 변경(내부용)", description = "주문 상태를 변경합니다. 내부 API 전용")
  @PatchMapping("/{orderId}/status")
  public ResponseEntity<ApiResponse> updateStatus(
      @Parameter(description = "상태를 변경할 주문 ID") @PathVariable UUID orderId,
      @RequestBody OrderUpdateStateRequest updateStateRequest) {
    orderService.updateStatus(orderId, updateStateRequest);
    return ApiResponse.ok();
  }

  @Operation(summary = "배달 담당자 변경(내부용)", description = "주문 배달 담당자를 변경합니다. 내부 API 전용")
  @PatchMapping("/{orderId}/delivery")
  public ResponseEntity<ApiResponse> updateDeliveryManager(
      @Parameter(description = "배달 담당자를 변경할 주문 ID") @PathVariable UUID orderId,
      @RequestBody OrderUpdateDeliveryManagerRequest updateDeliveryManagerRequest) {
    orderService.updateDeliveryManager(orderId, updateDeliveryManagerRequest);
    return ApiResponse.ok();
  }
}
