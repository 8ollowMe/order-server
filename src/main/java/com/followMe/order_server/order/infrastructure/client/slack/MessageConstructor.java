package com.followMe.order_server.order.infrastructure.client.slack;

import com.followMe.order_server.order.domain.Order;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MessageConstructor {

  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  public static String generateOrderCreatedMessage(
      Order order, List<String> waypoints, String deliveryManagerName) {

    StringBuilder waypointsContent = new StringBuilder();
    for (String waypoint : waypoints) {
      waypointsContent.append(waypoint).append(", ");
    }

    LocalDateTime createdAt = order.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDateTime();

    String deadline = createdAt.plusDays(2).format(FORMATTER);

    return String.format(
        """
                주문번호 : %s
                주문업체 : %s
                주문 시간 : %s
                주문 상품 : %s

                발송지 : %s
                경유지 : %s
                도착지 : %s

                배송담당자 : %s

                요청사항 : %s

                최종 발송 시한 : %s
                """,
        order.getOrderId(),
        order.getReceiverVendor().getVendorName(),
        order.getCreatedAt(),
        order.getProductInfo().getProductName(),
        order.getRequestVendor().getHubName(),
        waypointsContent,
        order.getReceiverVendor().getHubName(),
        deliveryManagerName,
        order.getRequestNote(),
        deadline);
  }
}
