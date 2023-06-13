package com.siddharth.application.repository.orderRepositories;

import com.siddharth.application.entity.orderEntities.OrderDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetailsEntity, Long> {

    List<OrderDetailsEntity> findByOrderId(Long orderId);
    List<OrderDetailsEntity> findByUserId(Long userid);
    List<OrderDetailsEntity> findByDeliveryDateLessThanEqual(LocalDate beforeDate);
    List<OrderDetailsEntity> findByDeliveryDateGreaterThanEqual(LocalDate afterDate);
    List<OrderDetailsEntity> findByDeliveryDateBetween(LocalDate beforeDate, LocalDate afterDate);
}
