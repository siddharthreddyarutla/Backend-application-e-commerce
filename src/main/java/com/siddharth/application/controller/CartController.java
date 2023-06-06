package com.siddharth.application.controller;

import com.siddharth.application.dto.*;
import com.siddharth.application.entity.CartOrWishlistEntity;
import com.siddharth.application.serviceImpl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartController {

    @Autowired
    CartServiceImpl cartServiceImpl;

    // add to cart
    @PostMapping(value = "/addToCart")
    private ResponseEntity<CartOrWishlistDto> addToCart(@RequestBody CartOrWishlistDto cartOrWishlistDto) {
        return new ResponseEntity<>(cartServiceImpl.addToCart(cartOrWishlistDto), HttpStatus.OK);
    }

    // get products added to cart
    @GetMapping(value = "/getProductsAddedToCart/{userId}")
    private ResponseEntity<List<ProductDto>> getAllProductsInCart(@PathVariable Long userId) {
        return new ResponseEntity<>(cartServiceImpl.getAllProductsFromCart(userId), HttpStatus.OK);
    }

    // get all complete products that are added to cart
    @GetMapping(value = "/getCompleteProductsDetailsAddedToCart")
    private ResponseEntity<List<CartCompleteDto>> getAllCompleteProductsAddedToCart(@RequestParam Long userId) {
        return new ResponseEntity<>(cartServiceImpl.getAllCompleteProductDetailsAddedToCart(userId), HttpStatus.OK);
    }

    // get the pre-order details of the products added to cart
    @GetMapping(value = "/getPreOrderDetailsOfCart")
    private ResponseEntity<PreOrderDetailsDto> getPreOrderDetailsOfCartByUserId(@RequestParam Long userId) {
        return new ResponseEntity<>(cartServiceImpl.getPreOrderDetailsOfCartByUserId(userId), HttpStatus.OK);
    }

    // remove product from the cart
    @DeleteMapping(value = "/removeProductFromCart")
    private ResponseEntity<String> removeItemFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        return new ResponseEntity<>(cartServiceImpl.deleteProductFromCart(userId, productId), HttpStatus.OK);
    }

    // buy or order products from the cart
    @PostMapping(value = "/orderProductItems")
    private ResponseEntity<OrderDetailsDto> orderProducts(@RequestBody OrderDetailsDto orderDetailsDto) {
        return new ResponseEntity<>(cartServiceImpl.orderProductItems(orderDetailsDto), HttpStatus.OK);
    }

    // set orderState
    @PutMapping(value = "/updateOrderState")
    private ResponseEntity<String> updateOrderState(@RequestParam Long orderId, @RequestParam String orderState) {
        return new ResponseEntity<>(cartServiceImpl.updateOrderState(orderId, orderState), HttpStatus.OK);
    }

    // delete all items from the cart or empty cart
    @DeleteMapping(value = "/emptyCart")
    private ResponseEntity<String> deleteCart(@RequestParam Long userId) {
        return new ResponseEntity<>(cartServiceImpl.deleteCart(userId), HttpStatus.OK);
    }

    // get all orders history
    @GetMapping(value = "/getAllOrders")
    private ResponseEntity<List<OrderDetailsDto>> getAllOrders() {
        return new ResponseEntity<>(cartServiceImpl.getAllOrderDetails(), HttpStatus.OK);
    }

    // get all orders history by user id
    @GetMapping(value = "/getMyOrders")
    private ResponseEntity<List<OrderDetailsDto>> getMyOrderDetails(@RequestParam Long userId) {
        return new ResponseEntity<>(cartServiceImpl.getMyOrders(userId), HttpStatus.OK);
    }
}
