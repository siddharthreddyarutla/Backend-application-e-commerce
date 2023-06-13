package com.siddharth.application.repository.userRepositories;

import com.siddharth.application.entity.userEntities.UserAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddressEntity, Long> {
    List<UserAddressEntity> findByUserId(Long userId);
    UserAddressEntity findByAddressId(Long addressId);
    List<UserAddressEntity> findByFullName(String name);
}