package com.siddharth.application.dto.cartDtos;

import com.siddharth.application.dto.orderDtos.PreOrderDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartPreOrderDetailsAndCartDto {

    CartCompleteDto cartCompleteDto;
    PreOrderDetailsDto preOrderDetailsDto;
}
