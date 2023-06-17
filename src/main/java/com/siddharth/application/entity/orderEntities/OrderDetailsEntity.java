package com.siddharth.application.entity.orderEntities;

import com.siddharth.application.dto.orderDtos.OrderDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ORDER_DETAILS")
public class OrderDetailsEntity {

    @Id
    @Column(name = "ORDER_ID")
    private Long orderId;
    @Column(name = "PRODUCT_IDS")
    private String productIds;
    @Column(name = "SHIPPING_ADDRESS_ID")
    private Long shippingAddressId;
    @Column(name = "BILLING_ADDRESS_ID")
    private Long billingAddressId;
    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;
    @Column(name = "TOTAL_ITEMS")
    private Long totalItems;
    @Column(name = "TOTAL_AMOUNT")
    private Double totalAmount;
    @Column(name = "DELIVERY_CHARGES")
    private Long deliveryCharges;
    @Column(name = "TAX_CHARGES")
    private Long taxCharges;
    @Column(name = "ORDER_AMOUNT")
    private Double orderAmount;

    public OrderDetailsDto toOrderDetailsDto() {
        return OrderDetailsDto.builder().orderId(orderId).productIds(productIds).shippingAddressId(shippingAddressId)
                .billingAddressId(billingAddressId).paymentMethod(paymentMethod).totalItems(totalItems)
                .totalAmount(totalAmount).deliveryCharges(deliveryCharges).taxCharges(taxCharges)
                .orderAmount(orderAmount).build();
    }
}
