package com.siddharth.application.dto.orderDtos;

import com.siddharth.application.entity.orderEntities.PreOrderDetailsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreOrderDetailsDto {
  private Long userId;
  private LocalDate expectedDeliveryDate;
  private String eligibleForFreeDelivery;
  private Long totalItemsInCart;
  private Double totalAmount;

  public PreOrderDetailsEntity toPreOrderDetailsEntity() {
    return PreOrderDetailsEntity.builder().userId(userId).expectedDeliveryDate(expectedDeliveryDate)
        .eligibleForFreeDelivery(eligibleForFreeDelivery).totalItemsInCart(totalItemsInCart)
        .totalAmount(totalAmount).build();
  }
}
