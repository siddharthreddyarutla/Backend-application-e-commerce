package com.siddharth.application.dto;

import com.siddharth.application.entity.CartOrWishlistEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartOrWishlistDto {
    private Long userId;
    private Long productId;
    private Long quantity;
    private Boolean wishList;

    public CartOrWishlistEntity toCartOrWishlistEntity() {
        return CartOrWishlistEntity.builder().userId(userId).productId(productId).quantity(quantity)
                .wishList(wishList).build();
    }
}
