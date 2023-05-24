package com.siddharth.application.entity;

import com.siddharth.application.dto.ProductInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PRODUCT_INFO")
public class ProductInfoEntity {
    @Id
    @Column(name = "PRODUCT_ID")
    private Long productId;
    @Column(name = "QUANTITY")
    private Long productQuantity;
    @Column(name = "STATE")
    private String productState;

    public ProductInfoDto toProductInfoDto() {
        return ProductInfoDto.builder().productQuantity(productQuantity)
                .productState(productState).build();
    }
}
