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
  private UUID hubId;

  public VendorInfo(UUID vendorId, String vendorName, UUID hubId) {
    if (vendorId == null) throw new IllegalArgumentException("vendorId 필수");
    if (vendorName == null || vendorName.isBlank())
      throw new IllegalArgumentException("vendorName 필수");
    if (hubId == null) throw new IllegalArgumentException("hubId 필수");

    this.vendorId = vendorId;
    this.vendorName = vendorName;
    this.hubId = hubId;
  }
}
