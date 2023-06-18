package com.siddharth.application.repository.cartRepositories;

import com.siddharth.application.entity.cartEntities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

    List<CartEntity> findByUserId(Long userId);

    CartEntity findByUserIdAndProductId(Long userId, Long productId);

    CartEntity findByProductId(Long productId);

}
