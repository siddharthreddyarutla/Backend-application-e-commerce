package com.siddharth.application.dto;

import com.siddharth.application.entity.ProductInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoDto {
    private Long productQuantity;
    private String productState;
    private LocalDate deliveryDate;
    public ProductInfoEntity toProductInfoEntity() {
        return ProductInfoEntity.builder().productQuantity(productQuantity)
                .productState(productState).deliveryDate(deliveryDate).build();
    }
}
