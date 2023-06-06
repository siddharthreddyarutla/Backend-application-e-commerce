package com.siddharth.application.repository;

import com.siddharth.application.entity.OrderDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetailsEntity, Long> {

    OrderDetailsEntity findByOrderId(Long orderId);
    List<OrderDetailsEntity> findByUserId(Long userid);
}
