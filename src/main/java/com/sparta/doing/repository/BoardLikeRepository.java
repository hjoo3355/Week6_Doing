package com.sparta.doing.repository;

import com.sparta.doing.entity.Board;
import com.sparta.doing.entity.BoardLike;
import com.sparta.doing.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByBoardAndUserEntity(Board board, UserEntity userEntity);
}
