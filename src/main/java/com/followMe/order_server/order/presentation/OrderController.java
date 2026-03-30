package com.followMe.order_server.order.presentation;

import com.followMe.common.response.ApiResponse;
import com.followMe.order_server.order.application.OrderService;
import com.followMe.order_server.order.application.dto.request.OrderCreateRequest;
import com.followMe.order_server.order.application.dto.response.OrderResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

  private final OrderService orderService;

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

}
