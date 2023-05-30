package com.siddharth.application.entity;

import com.siddharth.application.dto.PreOrderDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRE_ORDER_DETAILS")
public class PreOrderDetailsEntity {

    @Id
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "EXPECTED_DELIVERY_DATE")
    private LocalDate expectedDeliveryDate;

    @Column(name = "ORDER_ELIGIBILITY")
    private String eligibleForFreeDelivery;

    @Column(name = "TOTAL_ITEMS")
    private Long totalItemsInCart;

    @Column(name = "TOTAL_AMOUNT")
    private Double totalAmount;

    public PreOrderDetailsDto toPreOrderDetailsDto() {
        return PreOrderDetailsDto.builder().eligibleForFreeDelivery(eligibleForFreeDelivery)
                .expectedDeliveryDate(expectedDeliveryDate).totalItemsInCart(totalItemsInCart)
                .totalAmount(totalAmount).build();
    }
}
