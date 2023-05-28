package com.siddharth.application.service;

import com.siddharth.application.dto.CartCompleteDto;
import com.siddharth.application.dto.CartOrWishlistDto;
import com.siddharth.application.dto.ProductDto;
import com.siddharth.application.dto.ProductPrimaryDto;

import java.util.List;

public interface CartService {
    CartOrWishlistDto addToCart(CartOrWishlistDto cartOrWishlistDto);

    List<ProductDto> getAllProductsFromCart(Long userId);

    List<CartCompleteDto> getAllCompleteProductDetailsAddedToCart(Long userId);
}
