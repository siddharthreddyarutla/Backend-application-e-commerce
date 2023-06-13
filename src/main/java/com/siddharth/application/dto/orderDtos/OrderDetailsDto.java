package com.siddharth.application.dto.orderDtos;

import com.siddharth.application.entity.orderEntities.OrderDetailsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {
    private Long userId;
    private Long addressId;
    private String productId;
    private LocalDate orderPlacedDate;
    private LocalDate deliveryDate;
    private String paymentMethod;
    private Long totalItems;
    private Long deliveryCharges;
    private Double totalAmount;
    private String orderState;
    public OrderDetailsEntity toOrderDetailsEntity() {
        return OrderDetailsEntity.builder().userId(userId).addressId(addressId).productId(productId)
                .orderPlacedDate(orderPlacedDate).deliveryDate(deliveryDate).paymentMethod(paymentMethod)
                .totalItems(totalItems).deliveryCharges(deliveryCharges).totalAmount(totalAmount).orderState(orderState)
                .build();
    }
}
