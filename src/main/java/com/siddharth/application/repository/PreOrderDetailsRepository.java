package com.siddharth.application.repository;

import com.siddharth.application.entity.PreOrderDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PreOrderDetailsRepository extends JpaRepository<PreOrderDetailsEntity, Long> {

    PreOrderDetailsEntity findByUserId(Long userId);
}
