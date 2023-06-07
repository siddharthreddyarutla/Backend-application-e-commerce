package com.siddharth.application.repository;

import com.siddharth.application.dto.CartOrWishlistDto;
import com.siddharth.application.entity.CartOrWishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartOrWishlistEntity, Long> {

    List<CartOrWishlistEntity> findByUserId(Long userId);

    CartOrWishlistEntity findByUserIdAndProductId(Long userId, Long productId);

    CartOrWishlistEntity findByProductId(Long productId);

}
