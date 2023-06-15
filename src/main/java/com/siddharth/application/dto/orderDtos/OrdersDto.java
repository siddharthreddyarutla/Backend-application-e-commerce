package com.siddharth.application.dto.orderDtos;

import com.siddharth.application.entity.orderEntities.OrdersEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDto {
    private Long orderId;
    private Long userId;
    private Long addressId;
    private Long productId;
    private LocalDate orderPlacedDate;
    private LocalDate deliveryDate;
    private String orderState;

    public OrdersEntity toOrdersEntity() {
        return OrdersEntity.builder().orderId(orderId).userId(userId).addressId(addressId).productId(productId)
                .orderPlacedDate(orderPlacedDate).deliveryDate(deliveryDate).orderState(orderState)
                .build();
    }
}
