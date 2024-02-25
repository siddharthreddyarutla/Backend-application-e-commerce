package com.siddharth.application.repository.orderRepositories;

import com.siddharth.application.entity.orderEntities.OrderDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetailsEntity, Long> {

  List<OrderDetailsEntity> findByOrderId(Long orderId);
}
