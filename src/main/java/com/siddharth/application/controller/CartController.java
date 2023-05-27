package com.siddharth.application.controller;

import com.siddharth.application.dto.CartOrWishlistDto;
import com.siddharth.application.dto.ProductDto;
import com.siddharth.application.dto.ProductPrimaryDto;
import com.siddharth.application.service.CartService;
import com.siddharth.application.serviceImpl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
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
}
