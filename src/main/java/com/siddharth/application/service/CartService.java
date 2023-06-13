package com.siddharth.application.service;

import com.siddharth.application.dto.cartDtos.CartCompleteDto;
import com.siddharth.application.dto.cartDtos.CartOrWishlistDto;
import com.siddharth.application.dto.orderDtos.OrderDetailsCompleteDto;
import com.siddharth.application.dto.orderDtos.OrderDetailsDto;
import com.siddharth.application.dto.orderDtos.PreOrderDetailsDto;
import com.siddharth.application.dto.productDtos.ProductDto;

import java.time.LocalDate;
import java.util.List;

public interface CartService {
    CartOrWishlistDto addToCart(CartOrWishlistDto cartOrWishlistDto);

    List<ProductDto> getAllProductsFromCart(Long userId);

    List<CartCompleteDto> getAllCompleteProductDetailsAddedToCart(Long userId);

    PreOrderDetailsDto getPreOrderDetailsOfCartByUserId(Long userId);

    String deleteProductFromCart(Long userId, Long productId);

    String updateOrderState(Long orderId, String orderState);

    String deleteCart(Long userId);

}
