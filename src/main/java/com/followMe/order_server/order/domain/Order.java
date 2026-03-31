package com.followMe.order_server.order.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseAudit2 {

  @Id
  @Column(name = "order_id")
  private UUID orderId;

  @Column(name = "delivery_id")
  private UUID deliveryId;

  @Embedded private ProductInfo productInfo;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "vendorId", column = @Column(name = "request_vendor_id")),
    @AttributeOverride(name = "vendorName", column = @Column(name = "request_vendor_name")),
    @AttributeOverride(name = "hubId", column = @Column(name = "request_vendor_hub_id"))
  })
  private VendorInfo requestVendor;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "vendorId", column = @Column(name = "receiver_vendor_id")),
    @AttributeOverride(name = "vendorName", column = @Column(name = "receiver_vendor_name")),
    @AttributeOverride(name = "hubId", column = @Column(name = "receiver_vendor_hub_id"))
  })
  private VendorInfo receiverVendor;

  @Column(name = "request_note", columnDefinition = "TEXT")
  private String requestNote;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private OrderState status;

  @Column(name = "cancelled_at")
  private LocalDateTime cancelledAt;

  @Column(name = "cancelled_by")
  private UUID cancelledBy;

  @Builder
  private Order(
      UUID orderId,
      UUID deliveryId,
      ProductInfo productInfo,
      VendorInfo requestVendor,
      VendorInfo receiverVendor,
      String requestNote,
      OrderState status) {

    this.orderId = orderId;
    this.deliveryId = deliveryId;
    this.productInfo = productInfo;
    this.requestVendor = requestVendor;
    this.receiverVendor = receiverVendor;
    this.requestNote = requestNote;
    this.status = status;
  }

  public static Order ofCreate(
      UUID orderId,
      UUID deliveryId,
      ProductInfo productInfo,
      VendorInfo requestVendor,
      VendorInfo receiverVendor,
      String requestNote) {

    return Order.builder()
        .orderId(orderId)
        .deliveryId(deliveryId)
        .productInfo(productInfo)
        .requestVendor(requestVendor)
        .receiverVendor(receiverVendor)
        .requestNote(requestNote)
        .status(OrderState.CREATED)
        .build();
  }

  public void cancel(UUID userId) {
    this.status = OrderState.CANCELLED;
    this.cancelledAt = LocalDateTime.now();
    this.cancelledBy = userId;
  }
}
