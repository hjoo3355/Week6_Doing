package com.sparta.doing.controller;

import com.sparta.doing.controller.requestdto.BoardRequestDto;
import com.sparta.doing.controller.responsedto.BoardResponseDto;
import com.sparta.doing.entity.constant.SearchType;
import com.sparta.doing.service.BoardService;
import com.sparta.doing.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/boards")
@RestController
public class BoardController {
    private final BoardService boardService;
    private final PaginationService paginationService;

    @GetMapping
    public String paginateBoards(@RequestParam(required = false) SearchType searchType,
                                 @RequestParam(required = false) String searchValue,
                                 @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                 ModelMap map) {
        Page<BoardResponseDto> boards = boardService.searchBoards(searchType, searchValue, pageable).map(BoardResponseDto::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), boards.getTotalPages());

        map.addAttribute("boards", boards);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("searchTypes", SearchType.values());

        return "boards";
    }

    // 1개 게시판 내용 작성 후 BoardController의 @GetMapping으로 이동한 다음, index.html로 이동한다.
    @PostMapping("/form")
    public String createBoards(@RequestBody BoardRequestDto boardRequestDto,
                               @AuthenticationPrincipal UserDetails userDetails) {
        String userId = userDetails.getUsername();
        if (userId.isEmpty()) {
             return "login";
        }
        this.boardService.createBoard(boardRequestDto, userId);
        return "redirect:/boards";
    }

    // 1개 게시판 클릭 시 해당 게시판의 내용물과 게시글까지 전부 볼 수 있는 boards.html로 이동한다.
    // 1개 게시판 클릭 시 해당 게시판의 조회수 +1씩 증가.
    @GetMapping("/{boardId}")
    public String getOneBoard(@PathVariable(name = "boardId") Long boardId) {
        this.boardService.getOneBoardWithComments(boardId);
        return "boards";
    }

    // 1개 게시판 내용 수정 후 BoardController의 @GetMapping("/{boardId}")로 이동한 다음, boards.html로 이동한다.
    // 1개 게시판 내용 수정 Url로 이동 시 인증되지 않은 유저는 login.html로 이동한다.
    @PutMapping("/{boardId}")
    public String updateBoard(@PathVariable(name = "boardId") Long boardId,
                                                @RequestBody BoardRequestDto boardRequestDto,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        if (username.isEmpty()) {
            return "login";
        }

        this.boardService.updateBoard(boardId, boardRequestDto, username);
        return "redirect:/boards/{boardId}";
    }

    // 1개 게시판 삭제 후 BoardController의 @GetMapping으로 이동한 다음, index.html로 이동한다.
    // 1개 게시판 내용 수정 Url로 이동 시 인증되지 않은 유저는 login.html로 이동한다.
    @DeleteMapping("/{boardId}")
    public String deleteBoard(@PathVariable(name = "boardId") Long boardId,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        if (username.isEmpty()) {
            return "login";
        }

        this.boardService.deleteBoard(boardId, username);
        return "redirect:/boards";
    }
}
