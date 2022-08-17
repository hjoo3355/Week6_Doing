package com.sparta.doing.service;

import com.sparta.doing.controller.dto.BoardDto;
import com.sparta.doing.controller.requestdto.BoardRequestDto;
import com.sparta.doing.entity.Board;
import com.sparta.doing.entity.UserEntity;
import com.sparta.doing.entity.constant.SearchType;
import com.sparta.doing.repository.BoardRepository;
import com.sparta.doing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class BoardService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public Page<BoardDto> searchBoards(SearchType searchType, String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) { return boardRepository.findAll(pageable).map(BoardDto::from); }
        if (searchType.equals(SearchType.TITLE)) { return boardRepository.findByBoardTitleContaining(searchKeyword, pageable).map(BoardDto::from); }
        if (searchType.equals(SearchType.CONTENT)) { return boardRepository.findByBoardContentContaining(searchKeyword, pageable).map(BoardDto::from); }
        if (searchType.equals(SearchType.ID)) { return boardRepository.findByUserEntity_UsernameContaining(searchKeyword, pageable).map(BoardDto::from); }
        if (searchType.equals(SearchType.NICKNAME)) { return boardRepository.findByUserEntity_NicknameContaining(searchKeyword, pageable).map(BoardDto::from); }
        if (searchType.equals(SearchType.HASHTAG)) { return boardRepository.findByBoardHashtagContaining("#" + searchKeyword, pageable).map(BoardDto::from); }
        return boardRepository.findAll(pageable).map(BoardDto::from);
    }

    public void createBoard(BoardRequestDto boardRequestDto, String userId) {
        UserEntity foundUserEntity = userRepository.findByUsername(userId)
                .orElseThrow(() -> new UsernameNotFoundException("게시판 작성 권한이 없습니다."));

        Board createdBoard = Board.builder()
                .boardTitle(boardRequestDto.getBoardTitle())
                .authorName(foundUserEntity.getNickname())
                .boardContent(boardRequestDto.getBoardContent())
                .boardHashtag(boardRequestDto.getBoardHashtag())
                .build();

        createdBoard.mapToUserEntity(foundUserEntity);

        boardRepository.save(createdBoard);
    }

    // 1개 게시판 안에 들어있는 게시글까지 전부 가져와야 한다.
    // 코드 수정 필요.
    public void getOneBoardWithComments(Long boardId) {
        Board getOneBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));

        BoardRequestDto getOneBoardResult = BoardRequestDto.builder()
                .id(getOneBoard.getId())
                .boardTitle(getOneBoard.getBoardTitle())
                .boardContent(getOneBoard.getBoardContent())
                .authorName(getOneBoard.getAuthorName())
                .boardHashtag(getOneBoard.getBoardHashtag())
                .build();
        getOneBoard.visit();
    }

    public void updateBoard(Long boardId, BoardRequestDto boardRequestDto, String username) {
        Board foundBoardToUpdate = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
        if(foundBoardToUpdate.getUserEntity().getUsername().equals(username)){
            foundBoardToUpdate.update(boardRequestDto);
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
