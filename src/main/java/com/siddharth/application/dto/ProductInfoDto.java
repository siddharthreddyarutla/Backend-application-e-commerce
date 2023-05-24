package com.siddharth.application.dto;

import com.siddharth.application.entity.ProductInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoDto {
    private Long productQuantity;
    private String productState;

    public ProductInfoEntity toProductInfoEntity() {
        return ProductInfoEntity.builder().productQuantity(productQuantity)
                .productState(productState).build();
    }
}
