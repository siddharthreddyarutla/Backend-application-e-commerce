package com.siddharth.application.service;

import com.siddharth.application.dto.ProductDto;
import com.siddharth.application.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    ProductDto addProduct(ProductDto productDto);

    List<ProductDto> getDetails();

    ProductDto getDetailsById(Long productId);

    String deleteDetailsById(Long productId);

    ProductDto editDetailsById(Long productId, ProductDto productDto);

    ProductDto searchProductByCategory(String category);
}
