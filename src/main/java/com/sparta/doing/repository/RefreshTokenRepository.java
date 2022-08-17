package com.sparta.doing.repository;

import com.sparta.doing.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    // Optional<RefreshToken> findByKey(Long key);
}