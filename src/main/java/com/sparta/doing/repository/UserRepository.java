package com.sparta.doing.repository;

import com.sparta.doing.entity.UserEntity;
import com.sparta.doing.mapping.UserInfoMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<UserEntity> findByUsername(String username);

    UserInfoMapping findOneById(Long userId);
}
