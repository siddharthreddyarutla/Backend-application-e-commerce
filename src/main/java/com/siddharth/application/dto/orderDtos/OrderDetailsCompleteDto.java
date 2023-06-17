package com.siddharth.application.dto.orderDtos;

import com.siddharth.application.dto.productDtos.ProductDto;
import com.siddharth.application.dto.userDtos.UserAddressDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsCompleteDto {

    private Long userId;
    private List<ProductDto> productDtoList;
    private UserAddressDto shippingAddressDto;
    private UserAddressDto billingAddressDto;
    private Long quantity;
    private String paymentMethod;
    private LocalDate deliveryDate;
    private Long totalItems;
    private Long deliveryCharges;
    private Long taxCharges;
    private Double totalAmount;
}
