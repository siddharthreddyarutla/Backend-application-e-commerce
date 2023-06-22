package com.siddharth.application.dto.wishlistDtos;

import com.siddharth.application.entity.wishlistEntities.WishListEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishlistDto {
    private Long wishlistId;
    private String wishlistName;
    private Long userId;
    private Long productId;

    public WishListEntity toWishlistEntity() {
        return WishListEntity.builder().wishlistId(wishlistId).wishlistName(wishlistName).userId(userId)
                .productId(productId).build();
    }
}
