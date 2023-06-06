package com.siddharth.application.service;

import com.siddharth.application.dto.*;

import java.util.List;

public interface CartService {
    CartOrWishlistDto addToCart(CartOrWishlistDto cartOrWishlistDto);

    List<ProductDto> getAllProductsFromCart(Long userId);

    List<CartCompleteDto> getAllCompleteProductDetailsAddedToCart(Long userId);

    PreOrderDetailsDto getPreOrderDetailsOfCartByUserId(Long userId);

    String deleteProductFromCart(Long userId, Long productId);

    OrderDetailsDto orderProductItems(OrderDetailsDto orderDetailsDto);

    String updateOrderState(Long orderId, String orderState);

    String deleteCart(Long userId);

    List<OrderDetailsDto> getAllOrderDetails();

    List<OrderDetailsDto> getMyOrders(Long userId);
}
