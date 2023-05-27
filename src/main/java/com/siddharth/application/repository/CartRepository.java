package com.siddharth.application.repository;

import com.siddharth.application.dto.CartOrWishlistDto;
import com.siddharth.application.entity.CartOrWishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartOrWishlistEntity, Long> {

    List<CartOrWishlistEntity> findByUserId(Long userId);

    CartOrWishlistEntity findByUserIdAndProductId(Long userId, Long productId);
}
