package com.followMe.order_server.order.presentation;

import com.followMe.order_server.order.application.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderInternalController {

  private final OrderService orderService;
}
