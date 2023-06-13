package com.siddharth.application.dto.cartDtos;

import com.siddharth.application.entity.cartEntities.CartOrWishlistEntity;
import com.siddharth.application.entity.productEntities.ProductEntity;
import com.siddharth.application.entity.productEntities.ProductInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartCompleteDto {
    private Long productId;
    private String title;
    private String brand;
    private Long price;
    private String productState;
    private Long quantity;
    private String image;

    public CartCompleteDto(CartOrWishlistEntity cartOrWishlistEntity, ProductEntity productEntity,
                           ProductInfoEntity productInfoEntity) {
        this.productId = cartOrWishlistEntity.getProductId();
        this.title = productEntity.getTitle();
        this.brand = productEntity.getBrand();
        this.price = productEntity.getPrice();
        this.productState = productInfoEntity.getProductState();
        this.quantity = cartOrWishlistEntity.getQuantity();
        this.image = productEntity.getImage();
    }
}
