package com.followMe.order_server.order.domain;

import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VendorInfo {

  private UUID vendorId;
  private String vendorName;

  public VendorInfo(UUID vendorId, String vendorName) {
    if (vendorId == null) throw new IllegalArgumentException("vendorId 필수");
    if (vendorName == null || vendorName.isBlank())
      throw new IllegalArgumentException("vendorName 필수");

    this.vendorId = vendorId;
    this.vendorName = vendorName;
  }
}
