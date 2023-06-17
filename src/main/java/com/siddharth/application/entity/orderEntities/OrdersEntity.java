package com.siddharth.application.entity.orderEntities;

import com.siddharth.application.dto.orderDtos.OrdersDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ORDERS")
public class OrdersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long orderId;
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "ADDRESS_ID")
    private Long addressId;
    @Column(name = "PRODUCT_ID")
    private Long productId;
    @Column(name = "QUANTITY")
    private Long quantity;
    @Column(name = "ORDER_PLACED_DATE")
    private LocalDate orderPlacedDate;
    @Column(name = "DELIVERY_DATE")
    private LocalDate deliveryDate;
    @Column(name = "ORDER_STATE")
    private String orderState;

    public OrdersDto toOrdersDto() {
        return OrdersDto.builder().orderId(orderId).userId(userId).addressId(addressId).productId(productId)
                .quantity(quantity).orderPlacedDate(orderPlacedDate).deliveryDate(deliveryDate)
                .orderState(orderState).build();
    }

}
