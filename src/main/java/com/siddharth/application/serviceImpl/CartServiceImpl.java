package com.siddharth.application.serviceImpl;

import com.siddharth.application.dto.CartCompleteDto;
import com.siddharth.application.dto.CartOrWishlistDto;
import com.siddharth.application.dto.ProductDto;
import com.siddharth.application.entity.CartOrWishlistEntity;
import com.siddharth.application.entity.ProductEntity;
import com.siddharth.application.entity.ProductInfoEntity;
import com.siddharth.application.repository.CartRepository;
import com.siddharth.application.repository.ProductInfoRepository;
import com.siddharth.application.repository.ProductRepository;
import com.siddharth.application.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.siddharth.application.constants.Constants.CART;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Override
    public CartOrWishlistDto addToCart(CartOrWishlistDto cartOrWishlistDto) {
        CartOrWishlistEntity cartOrWishlistEntity = cartRepository
                .findByUserIdAndProductId(cartOrWishlistDto.getUserId(), cartOrWishlistDto.getProductId());
        if (cartOrWishlistEntity == null) {
            CartOrWishlistEntity cartOrWishlistEntity1 = cartOrWishlistDto.toCartOrWishlistEntity();
            cartRepository.save(cartOrWishlistEntity1);
        } else {
            Long quantity = cartOrWishlistEntity.getQuantity() + 1L;
            cartOrWishlistEntity.setQuantity(quantity);
            cartRepository.save(cartOrWishlistEntity);
        }
        return cartOrWishlistDto;
    }

    @Override
    public List<ProductDto> getAllProductsFromCart(Long userId) {
        List<CartOrWishlistEntity> cartOrWishlistEntityList = cartRepository.findByUserId(userId);

        if (!cartOrWishlistEntityList.isEmpty()) {
            List<ProductDto> productDtoList = new ArrayList<>();
            for (CartOrWishlistEntity cartOrWishlistEntity : cartOrWishlistEntityList) {
                if (cartOrWishlistEntity.getCartState().equals(CART)) {
                    ProductEntity productEntity = productRepository.findByProductId(cartOrWishlistEntity.getProductId());
                    ProductDto productDto = productEntity.toProductDto();
                    productDtoList.add(productDto);
                }
            }
            return productDtoList;
        }
        return new ArrayList<>(); // Return an empty list if cartOrWishlistEntityList is empty
    }

    @Override
    public List<CartCompleteDto> getAllCompleteProductDetailsAddedToCart(Long userId) {
        List<CartOrWishlistEntity> cartOrWishlistEntityList = cartRepository.findByUserId(userId);

        if (!cartOrWishlistEntityList.isEmpty()) {
            List<CartCompleteDto> cartCompleteDtoList = new ArrayList<>();
            for (CartOrWishlistEntity cartOrWishlistEntity : cartOrWishlistEntityList) {
                ProductEntity productEntity = productRepository.findByProductId(cartOrWishlistEntity.getProductId());
                ProductInfoEntity productInfoEntity = productInfoRepository
                        .findByProductId(cartOrWishlistEntity.getProductId());

                CartCompleteDto cartCompleteDto = new CartCompleteDto(cartOrWishlistEntity, productEntity, productInfoEntity);
                cartCompleteDtoList.add(cartCompleteDto);
            }
            return cartCompleteDtoList;
        }
        return new ArrayList<>();
    }
}
