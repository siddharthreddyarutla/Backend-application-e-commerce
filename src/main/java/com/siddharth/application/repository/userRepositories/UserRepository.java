package com.siddharth.application.repository.userRepositories;

import com.siddharth.application.entity.userEntities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findAll();
    UserEntity findByEmail(String email);
    UserEntity findByEmailAndPassword(String email, String password);
    UserEntity findByName(String name);
    Optional<UserEntity> findByUserId(Long id);
}
