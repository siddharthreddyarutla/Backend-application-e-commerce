package com.siddharth.application.controller;

import com.siddharth.application.dto.wishlistDtos.WishlistCompleteDto;
import com.siddharth.application.dto.wishlistDtos.WishlistDto;
import com.siddharth.application.serviceImpl.WishlistServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/wishlist")
public class WishlistController {

  @Autowired
  WishlistServiceImpl wishlistServiceImpl;

  // add product to wishlist
  @PostMapping(value = "/addToWishlist")
  private ResponseEntity<Long> addProductToWishlist(@RequestBody WishlistDto wishlistDto) {
    return new ResponseEntity<>(wishlistServiceImpl.addProductToWishlist(wishlistDto),
        HttpStatus.OK);
  }

  // get the list of wishlists
  @GetMapping(value = "/getMyWishlists")
  private ResponseEntity<List<String>> getMyWishlists(@RequestParam Long userId) {
    return new ResponseEntity<List<String>>(wishlistServiceImpl.getMyWishlists(userId),
        HttpStatus.OK);
  }

  // get my wishlist complete details
  @GetMapping(value = "/getMyCompleteWishlistDetails")
  private ResponseEntity<List<WishlistCompleteDto>> getMyCompleteWishlistDetails(
      @RequestParam Long userId) {
    return new ResponseEntity<>(wishlistServiceImpl.getMyCompleteWishlistDetails(userId),
        HttpStatus.OK);
  }

  // delete wishlist by wishlist name
  @DeleteMapping(value = "/deleteMyWishlist")
  private ResponseEntity<Long> deleteMyWishlist(@RequestParam Long userId,
      @RequestParam String wishlistName) {
    return new ResponseEntity<>(wishlistServiceImpl.deleteMyWishlist(userId, wishlistName),
        HttpStatus.OK);
  }

  // edit wishlist name
  @PutMapping(value = "/editMyWishlistByWishlistName")
  private ResponseEntity<List<String>> editMyWishlistByWishlistName(@RequestParam Long userId,
      @RequestParam String oldWishlistName, @RequestParam String newWishlistName) {
    return new ResponseEntity<>(
        wishlistServiceImpl.editMyWishlistByWishlistName(userId, oldWishlistName, newWishlistName),
        HttpStatus.OK);
  }
}
