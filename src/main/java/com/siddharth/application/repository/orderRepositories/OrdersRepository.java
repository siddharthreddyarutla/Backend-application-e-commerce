package com.siddharth.application.repository.orderRepositories;

import com.siddharth.application.entity.orderEntities.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {

    List<OrdersEntity> findByOrderId(Long orderId);

    List<OrdersEntity> findByUserId(Long userId);
}
