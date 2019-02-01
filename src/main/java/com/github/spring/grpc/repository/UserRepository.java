package com.github.spring.grpc.repository;

import com.github.spring.grpc.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT u.* FROM users u WHERE u.email = ?1", nativeQuery = true)
    UserEntity findByEmail(String email);

    UserEntity findByName(String name);
}
