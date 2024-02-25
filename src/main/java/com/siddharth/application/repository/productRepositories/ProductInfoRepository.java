package com.siddharth.application.repository.productRepositories;

import com.siddharth.application.entity.productEntities.ProductInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfoEntity, Long> {

  List<ProductInfoEntity> findAll();

  ProductInfoEntity findByProductId(Long productId);
}
