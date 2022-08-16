package com.sparta.doing.controller;

import com.sparta.doing.controller.request.BoardDto;
import com.sparta.doing.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/boards")
@RestController
public class BoardController {
    private final BoardService boardService;

    // 1개 게시판 내용 작성 후 BoardController의 @GetMapping으로 이동한 다음, index.html로 이동한다.
    @PostMapping
    public String createBoard(@RequestBody BoardDto boardDto,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        if (username.isEmpty()) {
             return "login";
        }

        this.boardService.createBoard(boardDto, username);
        return "redirect:/boards";
    }

    // 1개 게시판 클릭 시 해당 게시판의 내용물과 게시글까지 전부 볼 수 있는 boards.html로 이동한다.
    @GetMapping("/{boardId}")
    public String getOneBoard(@PathVariable(name = "boardId") Long boardId) {
        this.boardService.getOneBoardWithComments(boardId);
        return "boards";
    }

    // 1개 게시판 내용 수정 후 BoardController의 @GetMapping("/{boardId}")로 이동한 다음, boards.html로 이동한다.
    // 1개 게시판 내용 수정 Url로 이동 시 인증되지 않은 유저는 login.html로 이동한다.
    @PutMapping("/{boardId}")
    public String updateBoard(@PathVariable(name = "boardId") Long boardId,
                                                @RequestBody BoardDto boardDto,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        if (username.isEmpty()) {
            return "login";
        }

        this.boardService.updateBoard(boardId, boardDto, username);
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
