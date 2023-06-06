package com.siddharth.application.dto;

import com.siddharth.application.entity.OrderDetailsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
