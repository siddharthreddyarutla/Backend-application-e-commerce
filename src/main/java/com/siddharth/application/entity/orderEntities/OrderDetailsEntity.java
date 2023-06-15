package com.siddharth.application.entity.orderEntities;

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
    @Column(name = "ORDER_DETAILS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailsId;
    @Column(name = "ORDER_ID")
    private Long orderId;
    @Column(name = "SHIPPING_ADDRESS_ID")
    private Long shippingAddressId;
    @Column(name = "BILLING_ADDRESS_ID")
    private Long billingAddressId;
    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;
    @Column(name = "TOTAL_ITEMS")
    private Long totalItems;
    @Column(name = "DELIVERY_CHARGES")
    private Long deliveryCharges;
    @Column(name = "TAX_CHARGES")
    private Long taxCharges;
    @Column(name = "TOTAL_AMOUNT")
    private Double totalAmount;

}
