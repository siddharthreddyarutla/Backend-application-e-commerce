package com.siddharth.application.dto.cartDtos;

import com.siddharth.application.entity.cartEntities.CartEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
  private Long userId;
  private Long productId;
  private Long quantity;
  private String cartState;

  public CartEntity toCartOrWishlistEntity() {
    return CartEntity.builder().userId(userId).productId(productId).quantity(quantity)
        .cartState(cartState).build();
  }
}
