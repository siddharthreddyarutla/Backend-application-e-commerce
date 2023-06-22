package com.siddharth.application.serviceImpl;

import com.siddharth.application.dto.wishlistDtos.WishlistCompleteDto;
import com.siddharth.application.dto.wishlistDtos.WishlistDto;
import com.siddharth.application.entity.productEntities.ProductEntity;
import com.siddharth.application.entity.productEntities.ProductInfoEntity;
import com.siddharth.application.entity.wishlistEntities.WishListEntity;
import com.siddharth.application.repository.WishlistRepositories.WishlistRepository;
import com.siddharth.application.repository.productRepositories.ProductInfoRepository;
import com.siddharth.application.repository.productRepositories.ProductRepository;
import com.siddharth.application.service.WishlistService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Override
    public Long addProductToWishlist(WishlistDto wishlistDto) {
        if (ObjectUtils.isNotEmpty(wishlistDto)) {
            WishListEntity wishListEntity = wishlistRepository.findByProductId(wishlistDto.getProductId());
            if (ObjectUtils.isEmpty(wishListEntity)) {
                WishListEntity wishListEntity1 = wishlistDto.toWishlistEntity();
                wishlistRepository.save(wishListEntity1);
                return 1L;
            }
        }
        return 0L;
    }

    @Override
    public List<String> getMyWishlists(Long userId) {
        if (userId != null) {
            List<WishListEntity> wishListEntityList = wishlistRepository.findByUserId(userId);
            List<String> wishlistNameList = new ArrayList<>();
            if (!wishListEntityList.isEmpty()) {
                for (WishListEntity wishListEntity : wishListEntityList) {
                    if (!wishlistNameList.contains(wishListEntity.getWishlistName())) {
                        wishlistNameList.add(wishListEntity.getWishlistName());
                    }
                }
                return wishlistNameList;
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<WishlistCompleteDto> getMyCompleteWishlistDetails(Long userId) {
        List<WishListEntity> wishListEntityList = wishlistRepository.findByUserId(userId);
        List<WishlistCompleteDto> wishlistCompleteDtoList = new ArrayList<>();

        if (!wishListEntityList.isEmpty()) {
            for (WishListEntity wishListEntity : wishListEntityList) {
                WishlistCompleteDto wishlistCompleteDto = new WishlistCompleteDto();
                ProductEntity productEntity = productRepository.findByProductId(wishListEntity.getProductId());
                ProductInfoEntity productInfoEntity = productInfoRepository
                        .findByProductId(wishListEntity.getProductId());
                wishlistCompleteDto.setWishlistName(wishListEntity.getWishlistName());
                wishlistCompleteDto.setUserId(wishListEntity.getUserId());
                wishlistCompleteDto.setProductDto(productEntity.toProductDto());
                wishlistCompleteDto.setProductInfoDto(productInfoEntity.toProductInfoDto());
                if (ObjectUtils.isNotEmpty(wishlistCompleteDto)) {
                    wishlistCompleteDtoList.add(wishlistCompleteDto);
                }
            }
            return wishlistCompleteDtoList;
        }
        return new ArrayList<>();
    }

    @Override
    public Long deleteMyWishlist(Long userId, String wishlistName) {
        List<WishListEntity> wishListEntityList = wishlistRepository
                .findByUserIdAndWishlistName(userId, wishlistName);
        if (!wishListEntityList.isEmpty()) {
            for (WishListEntity wishListEntity : wishListEntityList) {
                wishlistRepository.delete(wishListEntity);
            }
            return 1L;
        }
        return 0L;
    }

    @Override
    public List<String> editMyWishlistByWishlistName(Long userId, String oldWishlistName, String newWishlistName) {
        List<WishListEntity> wishListEntityList = wishlistRepository
                .findByUserIdAndWishlistName(userId, oldWishlistName);
        List<String> wishlistNameList = new ArrayList<>();

        if (!wishListEntityList.isEmpty()) {
            for (WishListEntity wishListEntity : wishListEntityList) {
                wishListEntity.setWishlistName(newWishlistName);
                wishlistRepository.save(wishListEntity);
                wishlistNameList = getMyWishlists(userId);
            }
            return wishlistNameList;
        }
        return new ArrayList<>();
    }
}
