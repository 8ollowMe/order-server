package com.followMe.order_server.order.presentation;

import com.followMe.common.response.ApiResponse;
import com.followMe.order_server.order.application.OrderService;
import com.followMe.order_server.order.application.dto.request.OrderUpdateStateRequest;
import com.followMe.order_server.order.application.dto.response.OrderResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/orders")
public class OrderInternalController {

  private final OrderService orderService;

  @GetMapping("/{orderId}")
  public ResponseEntity<ApiResponse> readById(@PathVariable UUID orderId) {
    OrderResponse response = orderService.readById(orderId);
    return ApiResponse.ok(response);
  }

  @PatchMapping("/{orderId}/status")
  public ResponseEntity<ApiResponse> updateStatus(
      @PathVariable UUID orderId, @RequestBody OrderUpdateStateRequest updateStateRequest) {
    orderService.updateStatus(orderId, updateStateRequest);
    return ApiResponse.ok();
  }

  @DeleteMapping("/{orderId}")
  public ResponseEntity<ApiResponse> delete(@PathVariable UUID orderId) {
    orderService.softDeleteById(orderId);
    return ApiResponse.ok();
  }
}
