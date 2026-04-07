package com.followMe.order_server.order.domain;

import com.followMe.order_server.order.domain.exception.InvalidOrderQuantityException;
import com.followMe.order_server.order.domain.exception.InvalidStatusToCancelException;
import com.followMe.order_server.order.domain.exception.OrderStateTransitionNotAllowedException;
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
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "p_order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL")
public class Order extends BaseAudit2 {

  @Id
  @Column(name = "order_id")
  private UUID orderId;

  @Column(name = "delivery_id")
  private UUID deliveryId;

  @Column(name = "current_delivery_manager_id")
  private UUID currentDeliveryManagerId;

  @Embedded private ProductInfo productInfo;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "vendorId", column = @Column(name = "resource_vendor_id")),
    @AttributeOverride(name = "vendorName", column = @Column(name = "resource_vendor_name")),
    @AttributeOverride(name = "hubId", column = @Column(name = "resource_vendor_hub_id")),
    @AttributeOverride(name = "hubName", column = @Column(name = "resource_vendor_hub_name"))
  })
  private VendorInfo resourceVendor;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "vendorId", column = @Column(name = "receiver_vendor_id")),
    @AttributeOverride(name = "vendorName", column = @Column(name = "receiver_vendor_name")),
    @AttributeOverride(name = "hubId", column = @Column(name = "receiver_vendor_hub_id")),
    @AttributeOverride(name = "hubName", column = @Column(name = "receiver_vendor_hub_name"))
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
      UUID currentDeliveryManagerId,
      ProductInfo productInfo,
      VendorInfo resourceVendor,
      VendorInfo receiverVendor,
      String requestNote,
      OrderState status) {

    this.orderId = orderId;
    this.deliveryId = deliveryId;
    this.currentDeliveryManagerId = currentDeliveryManagerId;
    this.productInfo = productInfo;
    this.resourceVendor = resourceVendor;
    this.receiverVendor = receiverVendor;
    this.requestNote = requestNote;
    this.status = status;
  }

  public static Order ofCreate(
      UUID orderId,
      UUID deliveryId,
      UUID currentDeliveryManagerId,
      ProductInfo productInfo,
      VendorInfo requestVendor,
      VendorInfo receiverVendor,
      String requestNote) {

    return Order.builder()
        .orderId(orderId)
        .deliveryId(deliveryId)
        .currentDeliveryManagerId(currentDeliveryManagerId)
        .productInfo(productInfo)
        .resourceVendor(requestVendor)
        .receiverVendor(receiverVendor)
        .requestNote(requestNote)
        .status(OrderState.CREATED)
        .build();
  }

  private static boolean isPositive(int quantity) {
    return quantity < 0;
  }

  public void cancel(UUID userId) {
    validateStatusCancelAllowed(this.status);
    this.status = OrderState.CANCELLED;
    this.cancelledAt = LocalDateTime.now();
    this.cancelledBy = userId;
  }

  private void validateStatusCancelAllowed(OrderState status) {
    if (!isCancelable(status)) {
      throw new InvalidStatusToCancelException();
    }
  }

  public boolean isCancelable(OrderState status) {
    return status == OrderState.CREATED;
  }

  public void updateRequestNote(String requestNote) {
    this.requestNote = requestNote;
  }

  public void updateQuantity(int quantity) {
    validateQuantity(quantity);
    this.productInfo =
        new ProductInfo(
            this.productInfo.getProductId(), this.productInfo.getProductName(), quantity);
  }

  private void validateQuantity(int quantity) {
    if (!isPositive(quantity)) {
      throw new InvalidOrderQuantityException();
    }
  }

  public void updateState(OrderState updatedStatus) {
    validateStatusTransitionAllowed(updatedStatus);
    this.status = updatedStatus;
  }

  private void validateStatusTransitionAllowed(OrderState updatedStatus) {
    if (!this.status.canTransitionTo(updatedStatus)) {
      throw new OrderStateTransitionNotAllowedException();
    }
  }

  public void updateDeliveryManager(UUID updatedDeliveryManagerId) {
    this.currentDeliveryManagerId = updatedDeliveryManagerId;
  }
}
