package com.followMe.order_server.order.presentation;

import com.followMe.common.response.ApiResponse;
import com.followMe.order_server.order.application.OrderService;
import com.followMe.order_server.order.application.dto.request.OrderCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<ApiResponse> create(@RequestBody OrderCreateRequest createRequest) {
    orderService.create(createRequest);
    return ApiResponse.created();
  }
}
