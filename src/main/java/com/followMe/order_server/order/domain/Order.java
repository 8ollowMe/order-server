package com.followMe.order_server.order.domain;

import com.followMe.common.entity.BaseAudit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Order extends BaseAudit {

  @Id
  @Column(name = "order_id")
  private UUID orderId;

  @Column(name = "delivery_info_id", nullable = false)
  private UUID deliveryInfoId;

  @Column(name = "product_id", nullable = false)
  private UUID productId;

  @Column(name = "product_name", nullable = false, length = 255)
  private String productName;

  @Column(name = "request_vendor_id", nullable = false)
  private UUID requestVendorId;

  @Column(name = "request_vendor_name", nullable = false, length = 255)
  private String requestVendorName;

  @Column(name = "receiver_vendor_id", nullable = false)
  private UUID receiverVendorId;

  @Column(name = "receiver_vendor_name", nullable = false, length = 255)
  private String receiverVendorName;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;

  @Column(name = "request_note", columnDefinition = "TEXT")
  private String requestNote;

  //    @Enumerated(EnumType.STRING)
  //    @Column(name = "status", nullable = false)
  //    private OrderStatus status;

  @Column(name = "cancelled_at")
  private LocalDateTime cancelledAt;

  @Column(name = "cancelled_by")
  private UUID cancelledBy;
}
