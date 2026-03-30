package com.followMe.order_server.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductInfo {

  @Column(name = "product_id", nullable = false)
  private UUID productId;

  @Column(name = "product_name", nullable = false, length = 255)
  private String productName;

  @Column(name = "quantity", nullable = false)
  private int quantity;

  public ProductInfo(UUID productId, String productName, int quantity) {
    if (productId == null) {
      throw new IllegalArgumentException("productId 필수");
    }
    if (productName == null || productName.isBlank()) {
      throw new IllegalArgumentException("productName 필수");
    }
    if (quantity <= 0) {
      throw new IllegalArgumentException("수량은 0보다 커야 합니다");
    }
    this.productId = productId;
    this.productName = productName;
    this.quantity = quantity;
  }
}
