package com.siddharth.application.dto.orderDtos;

import com.siddharth.application.entity.orderEntities.OrderDetailsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {

    private Long orderId;
    private Long shippingAddressId;
    private Long billingAddressId;
    private String paymentMethod;
    private Long totalItems;
    private Long deliveryCharges;
    private Long taxCharges;
    private Double totalAmount;

    public OrderDetailsEntity toOrderDetailsEntity() {
        return OrderDetailsEntity.builder().orderId(orderId).shippingAddressId(shippingAddressId)
                .billingAddressId(billingAddressId).paymentMethod(paymentMethod).totalItems(totalItems)
                .deliveryCharges(deliveryCharges).taxCharges(taxCharges).totalAmount(totalAmount).build();
    }
}
