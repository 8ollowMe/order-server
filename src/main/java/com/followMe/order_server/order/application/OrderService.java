package com.followMe.order_server.order.application;

import com.followMe.common.pagination.CursorRequest;
import com.followMe.common.pagination.CursorResponse;
import com.followMe.order_server.order.application.dto.request.OrderCreateRequest;
import com.followMe.order_server.order.application.dto.request.OrderSearchCondition;
import com.followMe.order_server.order.application.dto.request.OrderUpdateDeliveryManagerRequest;
import com.followMe.order_server.order.application.dto.request.OrderUpdateRequest;
import com.followMe.order_server.order.application.dto.request.OrderUpdateStateRequest;
import com.followMe.order_server.order.application.dto.request.UserContext;
import com.followMe.order_server.order.application.dto.response.OrderResponse;
import java.util.UUID;

public interface OrderService {
  void create(OrderCreateRequest createRequest);

  OrderResponse readById(UUID orderId);

  CursorResponse<OrderResponse> search(
      CursorRequest cursorRequest, OrderSearchCondition condition, UserContext userContext);

  void update(UUID orderId, OrderUpdateRequest updateRequest);

  void updateStatus(UUID orderId, OrderUpdateStateRequest updateStateRequest);

  void softDeleteById(UUID orderId);

  void updateDeliveryManager(
      UUID orderId, OrderUpdateDeliveryManagerRequest updateDeliveryManagerRequest);
}
