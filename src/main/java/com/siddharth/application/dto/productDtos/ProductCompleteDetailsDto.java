package com.siddharth.application.dto.productDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCompleteDetailsDto {
    ProductDto productDto;
    ProductInfoDto productInfoDto;
}
