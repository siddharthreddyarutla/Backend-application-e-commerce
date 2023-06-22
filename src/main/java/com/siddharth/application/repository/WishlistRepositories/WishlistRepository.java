package com.siddharth.application.repository.WishlistRepositories;

import com.siddharth.application.entity.wishlistEntities.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<WishListEntity, Long> {
    WishListEntity findByProductId(Long productId);

    List<WishListEntity> findByUserId(Long userId);

    List<WishListEntity> findByUserIdAndWishlistName(Long userId, String wishlistName);
}
