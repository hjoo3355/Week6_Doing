package com.sparta.doing.service;

import com.sparta.doing.controller.dto.BoardDto;
import com.sparta.doing.controller.requestdto.BoardRequestDto;
import com.sparta.doing.controller.responsedto.BoardResponseDto;
import com.sparta.doing.entity.Board;
import com.sparta.doing.entity.UserEntity;
import com.sparta.doing.entity.constant.SearchType;
import com.sparta.doing.exception.BoardNotFoundException;
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
    public Page<BoardDto> searchBoards(SearchType searchType,
                                       String searchKeyword,
                                       Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return boardRepository.findAll(pageable).map(BoardDto::from);
        }
        if (searchType.equals(SearchType.TITLE)) {
            return boardRepository.findByBoardTitleContaining(searchKeyword, pageable).map(BoardDto::from);
        }
        if (searchType.equals(SearchType.CONTENT)) {
            return boardRepository.findByBoardContentContaining(searchKeyword, pageable).map(BoardDto::from);
        }
        if (searchType.equals(SearchType.ID)) {
            return boardRepository.findByUserEntity_UsernameContaining(searchKeyword, pageable).map(BoardDto::from);
        }
        if (searchType.equals(SearchType.NICKNAME)) {
            return boardRepository.findByUserEntity_NicknameContaining(searchKeyword, pageable).map(BoardDto::from);
        }
        if (searchType.equals(SearchType.HASHTAG)) {
            return boardRepository.findByBoardHashtagContaining("#" + searchKeyword, pageable).map(BoardDto::from);
        }
        return boardRepository.findAll(pageable).map(BoardDto::from);
    }

    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, Long userId) {
        UserEntity foundUserEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("게시판 작성 권한이 없습니다."));

        Board createdBoard = Board.builder()
                .boardTitle(boardRequestDto.getBoardTitle())
                .authorName(foundUserEntity.getNickname())
                .boardContent(boardRequestDto.getBoardContent())
                .boardHashtag(boardRequestDto.getBoardHashtag())
                .build();

        createdBoard.mapToUserEntity(foundUserEntity);

        return BoardResponseDto.from(boardRepository.save(createdBoard));
    }

    // 1개 게시판 안에 들어있는 게시글까지 전부 가져와야 한다.
    // 코드 수정 필요.
    public BoardResponseDto getOneBoardWithComments(Long boardId) {
        // db에서 게시판 검색
        Board getOneBoard = boardRepository.findById(boardId)
                .orElseThrow(
                        () -> new BoardNotFoundException(
                                "해당 게시판은 존재하지 않습니다."));
        // 조회수 증가
        getOneBoard.visit();

        // 반환

        return BoardResponseDto.builder()
                .id(getOneBoard.getId())
                .boardTitle(getOneBoard.getBoardTitle())
                .boardContent(getOneBoard.getBoardContent())
                .authorName(getOneBoard.getAuthorName())
                .boardHashtag(getOneBoard.getBoardHashtag())
                .countBoardVisit(getOneBoard.getCountBoardVisit())
                .createdAt(getOneBoard.getCreatedAt())
                .build();
    }

    public void updateBoard(Long boardId, BoardRequestDto
            boardRequestDto, Long userId) {
        // 수정하려는 게시글 검색
        Board foundBoardToUpdate = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("해당 게시글은 존재하지 않습니다."));
        // 게시글 작성자와 현재 로그인한 유저의 userId를 비교하여 작성자인지 확인
        if (foundBoardToUpdate.getUserEntity().getId().equals(userId)) {
            foundBoardToUpdate.update(boardRequestDto);
        }
    }

    public void deleteBoard(Long boardId, Long userId) {
        // 삭제하려는 게시글 검색
        Board foundBoardToDelete = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("해당 게시글은 존재하지 않습니다."));
        // 게시글 작성자와 현재 로그인한 유저의 userId를 비교하여 작성자인지 확인
        if (foundBoardToDelete.getUserEntity().getId().equals(userId)) {
            boardRepository.delete(foundBoardToDelete);
        }
    }
}
