package com.siddharth.application.repository;

import com.siddharth.application.entity.ProductInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductInfoRepository extends JpaRepository<ProductInfoEntity, Long> {

    List<ProductInfoEntity> findAll();
    ProductInfoEntity findByProductId(Long productId);
}
