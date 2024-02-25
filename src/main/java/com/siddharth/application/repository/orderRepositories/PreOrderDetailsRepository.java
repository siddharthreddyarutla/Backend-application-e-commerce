package com.siddharth.application.repository.orderRepositories;

import com.siddharth.application.entity.orderEntities.PreOrderDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreOrderDetailsRepository extends JpaRepository<PreOrderDetailsEntity, Long> {

  PreOrderDetailsEntity findByUserId(Long userId);
}
