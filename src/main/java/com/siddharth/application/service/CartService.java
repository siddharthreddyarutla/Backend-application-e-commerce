package com.siddharth.application.service;

import com.siddharth.application.dto.cartDtos.CartCompleteDto;
import com.siddharth.application.dto.cartDtos.CartDto;
import com.siddharth.application.dto.cartDtos.CartPreOrderDetailsAndCartDto;
import com.siddharth.application.dto.orderDtos.PreOrderDetailsDto;
import com.siddharth.application.dto.productDtos.ProductDto;

import java.util.List;

public interface CartService {
    CartDto addToCart(CartDto cartDto);

    List<ProductDto> getAllProductsFromCart(Long userId);

    List<CartCompleteDto> getAllCompleteProductDetailsAddedToCart(Long userId);

    PreOrderDetailsDto getPreOrderDetailsOfCartByUserId(Long userId);

    CartPreOrderDetailsAndCartDto deleteProductFromCart(Long userId, Long productId);

    String updateOrderState(Long orderId, String orderState);

    String deleteCart(Long userId);

    PreOrderDetailsDto editProductQuantityInCart(Long userId, Long productId, Long quantity);
}
