package com.siddharth.application.entity;

import com.siddharth.application.dto.ProductInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

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

    @Column(name = "AVAILABLE_QUANTITY")
    private Long productQuantity;

    @Column(name = "STATE")
    private String productState;

    @Column(name = "DELIVERY_DATE")
    private LocalDate deliveryDate;

    public ProductInfoDto toProductInfoDto() {
        return ProductInfoDto.builder().productQuantity(productQuantity)
                .productState(productState).deliveryDate(deliveryDate).build();
    }
}
