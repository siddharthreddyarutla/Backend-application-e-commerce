package com.siddharth.application.controller;

import com.siddharth.application.dto.cartDtos.CartCompleteDto;
import com.siddharth.application.dto.cartDtos.CartDto;
import com.siddharth.application.dto.cartDtos.CartPreOrderDetailsAndCartDto;
import com.siddharth.application.dto.orderDtos.PreOrderDetailsDto;
import com.siddharth.application.dto.productDtos.ProductDto;
import com.siddharth.application.serviceImpl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
  private ResponseEntity<CartDto> addToCart(@RequestBody CartDto cartDto) {
    return new ResponseEntity<>(cartServiceImpl.addToCart(cartDto), HttpStatus.OK);
  }

  // get products added to cart
  @GetMapping(value = "/getProductsAddedToCart/{userId}")
  private ResponseEntity<List<ProductDto>> getAllProductsInCart(@PathVariable Long userId) {
    return new ResponseEntity<>(cartServiceImpl.getAllProductsFromCart(userId), HttpStatus.OK);
  }

  // get all complete products that are added to cart
  @GetMapping(value = "/getCompleteProductsDetailsAddedToCart")
  private ResponseEntity<List<CartCompleteDto>> getAllCompleteProductsAddedToCart(
      @RequestParam Long userId) {
    return new ResponseEntity<>(cartServiceImpl.getAllCompleteProductDetailsAddedToCart(userId),
        HttpStatus.OK);
  }

  // get the pre-order details of the products added to cart
  @GetMapping(value = "/getPreOrderDetailsOfCart")
  private ResponseEntity<PreOrderDetailsDto> getPreOrderDetailsOfCartByUserId(
      @RequestParam Long userId) {
    return new ResponseEntity<>(cartServiceImpl.getPreOrderDetailsOfCartByUserId(userId),
        HttpStatus.OK);
  }

  // remove product from the cart
  @DeleteMapping(value = "/removeProductFromCart")
  private ResponseEntity<CartPreOrderDetailsAndCartDto> removeItemFromCart(
      @RequestParam Long userId, @RequestParam Long productId) {
    return new ResponseEntity<>(cartServiceImpl.deleteProductFromCart(userId, productId),
        HttpStatus.OK);
  }

  // set orderState
  @PutMapping(value = "/updateOrderState")
  private ResponseEntity<String> updateOrderState(@RequestParam Long orderId,
      @RequestParam String orderState) {
    return new ResponseEntity<>(cartServiceImpl.updateOrderState(orderId, orderState),
        HttpStatus.OK);
  }

  // delete all items from the cart or empty cart
  @DeleteMapping(value = "/emptyCart")
  private ResponseEntity<String> deleteCart(@RequestParam Long userId) {
    return new ResponseEntity<>(cartServiceImpl.deleteCart(userId), HttpStatus.OK);
  }

  // edit quantity in the cart
  @PutMapping(value = "/editProductQuantityInCart")
  private ResponseEntity<PreOrderDetailsDto> editQuantityOfProductInCart(@RequestParam Long userId,
      @RequestParam Long productId, @RequestParam Long quantity) {
    return new ResponseEntity<>(
        cartServiceImpl.editProductQuantityInCart(userId, productId, quantity), HttpStatus.OK);
  }

  // get all complete products that are added to save for later
  @GetMapping(value = "/getCompleteProductsDetailsAddedToSaveForLater")
  private ResponseEntity<List<CartCompleteDto>> getAllCompleteProductsAddedToSaveForLater(
      @RequestParam Long userId) {
    return new ResponseEntity<>(
        cartServiceImpl.getAllCompleteProductDetailsAddedToSaveForLater(userId), HttpStatus.OK);
  }

  // remove product from the cart
  @DeleteMapping(value = "/removeProductFromSaveForLater")
  private ResponseEntity<String> removeItemFromSaveForLater(@RequestParam Long userId,
      @RequestParam Long productId) {
    return new ResponseEntity<>(cartServiceImpl.deleteProductFromSaveForLater(userId, productId),
        HttpStatus.OK);
  }
}

