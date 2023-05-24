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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_INFO_ID")
    private Long productInfoId;
    @Column(name = "PRODUCT_ID")
    private Long productId;
    @Column(name = "QUANTITY")
    private Long productQuantity;
    @Column(name = "STATE")
    private String productState;

    public ProductInfoDto toProductInfoDto() {
        return ProductInfoDto.builder().productId(productId).productQuantity(productQuantity)
                .productState(productState).build();
    }
}