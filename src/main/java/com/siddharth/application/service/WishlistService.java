package com.siddharth.application.service;

import com.siddharth.application.dto.wishlistDtos.WishlistCompleteDto;
import com.siddharth.application.dto.wishlistDtos.WishlistDto;

import java.util.List;

public interface WishlistService {
    Long addProductToWishlist(WishlistDto wishlistDto);

    List<String> getMyWishlists(Long userId);

    List<WishlistCompleteDto> getMyCompleteWishlistDetails(Long userId);

    Long deleteMyWishlist(Long userId, String wishlistName);

    List<String> editMyWishlistByWishlistName(Long userId, String oldWishlistName, String newWishlistName);
}
