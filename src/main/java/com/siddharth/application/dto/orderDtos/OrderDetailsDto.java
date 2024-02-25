package com.siddharth.application.dto.orderDtos;

import com.siddharth.application.entity.orderEntities.OrderDetailsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {

  private Long orderId;
  private String productIds;
  private Long shippingAddressId;
  private Long billingAddressId;
  private String paymentMethod;
  private Long totalItems;
  private Double totalAmount;
  private Long deliveryCharges;
  private Long taxCharges;
  private Double orderAmount;

  public OrderDetailsEntity toOrderDetailsEntity() {
    return OrderDetailsEntity.builder().orderId(orderId).productIds(productIds)
        .shippingAddressId(shippingAddressId).billingAddressId(billingAddressId)
        .paymentMethod(paymentMethod).totalItems(totalItems).totalAmount(totalAmount)
        .deliveryCharges(deliveryCharges).taxCharges(taxCharges).orderAmount(orderAmount).build();
  }
}
