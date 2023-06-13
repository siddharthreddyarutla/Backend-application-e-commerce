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
    private UserAddressDto userAddressDto;
    private List<ProductDto> productDtoList;
    private LocalDate orderPlacedDate;
    private LocalDate deliveryDate;
    private String paymentMethod;
    private Long totalItems;
    private Long deliveryCharges;
    private Double totalAmount;
    private String orderState;
}
