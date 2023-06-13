package com.siddharth.application.dto.productDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPrimaryDto {
    private Long productId;
    private String title;
    private String brand;
    private Long price;
    private String description;
    private String image;
    private String productState;

    public ProductPrimaryDto(ProductDto productDto, ProductInfoDto productInfoDto) {
        this.productId = productDto.getProductId();
        this.title = productDto.getTitle();
        this.brand = productDto.getBrand();
        this.price = productDto.getPrice();
        this.description = productDto.getDescription();
        this.image = productDto.getImage();
        this.productState = productInfoDto.getProductState();
    }
}