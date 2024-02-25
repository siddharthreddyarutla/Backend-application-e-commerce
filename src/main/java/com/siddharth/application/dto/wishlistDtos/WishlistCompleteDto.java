package com.siddharth.application.dto.wishlistDtos;

import com.siddharth.application.dto.productDtos.ProductDto;
import com.siddharth.application.dto.productDtos.ProductInfoDto;
import com.siddharth.application.entity.productEntities.ProductInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishlistCompleteDto {
  private String wishlistName;
  private Long userId;
  private ProductDto productDto;
  private ProductInfoDto productInfoDto;
}
