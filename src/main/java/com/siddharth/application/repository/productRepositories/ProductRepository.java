package com.siddharth.application.repository.productRepositories;

import com.siddharth.application.entity.productEntities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

  List<ProductEntity> findAll();

  ProductEntity findByProductId(Long productId);

  List<ProductEntity> findByCategory(String category);

  List<ProductEntity> findByTitle(String title);

  List<ProductEntity> findByBrand(String brand);

  List<ProductEntity> findByPriceLessThanEqual(Long price);

  List<ProductEntity> findByPriceGreaterThanEqual(Long price);

  List<ProductEntity> findByPriceBetween(Long leastPrice, Long highestPrice);

  List<ProductEntity> findAllByOrderByRatingAsc();

  List<ProductEntity> findAllByOrderByRatingDesc();

  ProductEntity findByProductIdAndTitle(Long productId, String attribute);

  ProductEntity findByProductIdAndCategory(Long productId, String attribute);
}
