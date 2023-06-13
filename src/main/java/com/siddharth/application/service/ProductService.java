package com.siddharth.application.service;

import com.siddharth.application.dto.productDtos.ProductCompleteDetailsDto;
import com.siddharth.application.dto.productDtos.ProductDto;
import com.siddharth.application.dto.productDtos.ProductInfoDto;
import com.siddharth.application.dto.productDtos.ProductPrimaryDto;

import java.util.List;

public interface ProductService {
    ProductDto addProduct(ProductDto productDto);

    List<ProductDto> getDetails();

    ProductDto getDetailsById(Long productId);

    String deleteDetailsById(Long productId);

    ProductDto editDetailsById(Long productId, ProductDto productDto);

    List<ProductDto> searchProductByCategory(String category);

    List<ProductDto> searchProductByTitle(String title);

    List<ProductDto> searchProductByBrand(String brand);


    List<ProductDto> searchByHighestPrice(Long highestPrice);

    List<ProductDto> searchByLeastPriceAndHighestPrice(Long leastPrice, Long highestPrice);

    List<ProductDto> searchByLeastPrice(Long leastPrice);

    List<ProductDto> sortByRatingAsc();

    List<ProductDto> sortByRatingDesc();

    List<ProductDto> sortByDiscountAsc();

    List<ProductDto> sortByDiscountDesc();

    ProductCompleteDetailsDto postCompleteDetailsOfProduct(ProductCompleteDetailsDto productCompleteDetails);

    List<ProductCompleteDetailsDto> getAllCompleteProductDetails();

    ProductCompleteDetailsDto getCompleteProductDetailsByProductId(Long productId);

    ProductCompleteDetailsDto putCompleteProductDetailsByProductId(Long productId,
                                                                   ProductCompleteDetailsDto productCompleteDetailsDto);

    ProductInfoDto setStateOfProduct(Long productId, String productState);

    ProductInfoDto putProductQuantityByProductId(Long productId, Long productQuantity);
    String deleteCompleteProductDetailsByProductId(Long productId);

    List<ProductPrimaryDto> getAllPrimaryProductDetails();
}
