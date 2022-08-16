package com.sparta.doing.service;

import com.sparta.doing.controller.request.BoardDto;
import com.sparta.doing.entity.Board;
import com.sparta.doing.entity.UserEntity;
import com.sparta.doing.repository.BoardRepository;
import com.sparta.doing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class BoardService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public BoardDto createBoard(BoardDto boardDto, String username) {
        UserEntity foundUserEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("게시판 작성 권한이 없습니다."));

        Board createdBoard = Board.builder()
                .boardTitle(boardDto.getBoardTitle())
                .authorName(foundUserEntity.getNickname())
                .boardContent(boardDto.getBoardContent())
                .boardHashtag(boardDto.getBoardHashtag())
                .build();

        createdBoard.mapToUserEntity(foundUserEntity);

        Board savedBoard = boardRepository.save(createdBoard);

        BoardDto createdBoardResult = BoardDto.builder().id(savedBoard.getId()).build();

        return createdBoardResult;
    }

    // 1개 게시판 안에 들어있는 게시글까지 전부 가져와야 한다.
    // 코드 수정 필요.
    public BoardDto getOneBoardWithComments(Long boardId) {
        Board getOneBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));

        BoardDto getOneBoardResult = BoardDto.builder()
                .id(getOneBoard.getId())
                .boardTitle(getOneBoard.getBoardTitle())
                .boardContent(getOneBoard.getBoardContent())
                .authorName(getOneBoard.getAuthorName())
                .boardHashtag(getOneBoard.getBoardHashtag())
                .build();

        return getOneBoardResult;
    }

    public void updateBoard(Long boardId, BoardDto boardDto, String username) {
        Board foundBoardToUpdate = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
        if(foundBoardToUpdate.getUserEntity().getUsername().equals(username)){
            foundBoardToUpdate.update(boardDto);
        }
    }

    public void deleteBoard(Long boardId, String username) {
        Board foundBoardToDelete = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
        if(foundBoardToDelete.getUserEntity().getUsername().equals(username)){
            boardRepository.delete(foundBoardToDelete);
        }
    }
}
