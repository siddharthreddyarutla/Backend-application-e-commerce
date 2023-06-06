package com.siddharth.application.entity;

import com.siddharth.application.dto.OrderDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import javax.print.attribute.standard.MediaSize;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ORDER_DETAILS")
public class OrderDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long orderId;
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "ADDRESS_ID")
    private Long addressId;
    @Column(name = "PRODUCT_ID")
    private String productId;
    @Column(name = "ORDER_PLACED_DATE")
    private LocalDate orderPlacedDate;
    @Column(name = "DELIVERY_DATE")
    private LocalDate deliveryDate;
    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;
    @Column(name = "TOTAL_ITEMS")
    private Long totalItems;
    @Column(name = "DELIVERY_CHARGES")
    private Long deliveryCharges;
    @Column(name = "TOTAL_AMOUNT")
    private Double totalAmount;
    @Column(name = "ORDER_STATE")
    private String orderState;

    public OrderDetailsDto toOrderDetailsEntity() {
        return OrderDetailsDto.builder().userId(userId).addressId(addressId).productId(productId)
                .orderPlacedDate(orderPlacedDate).deliveryDate(deliveryDate).paymentMethod(paymentMethod)
                .totalItems(totalItems).deliveryCharges(deliveryCharges).totalAmount(totalAmount).orderState(orderState)
                .build();
    }
}
