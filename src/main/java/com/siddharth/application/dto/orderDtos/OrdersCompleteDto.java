package com.siddharth.application.dto.orderDtos;

import com.siddharth.application.dto.productDtos.ProductDto;
import com.siddharth.application.dto.userDtos.UserAddressDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersCompleteDto {
    private Long orderId;
    private Long userId;
    private UserAddressDto userAddressDto;
    private ProductDto productDto;
    private Long quantity;
    private LocalDate orderPlacedDate;
    private LocalDate deliveryDate;
    private String orderState;
}
