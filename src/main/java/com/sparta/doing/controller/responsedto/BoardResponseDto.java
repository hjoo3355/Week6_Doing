package com.sparta.doing.controller.responsedto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.doing.controller.dto.BoardDto;
import lombok.*;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardResponseDto {
    private Long id;
    private String boardTitle;
    private String authorName;
    private String boardContent;
    private String boardHashtag;
    private  int countBoardVisit;
    private String email;
    private String nickname;
    private String createdAt;

    public static BoardResponseDto from(BoardDto boardDto) {

        String getNickname = boardDto.getUserDto().getNickname();

        if (getNickname == null || getNickname.isBlank()) {
            getNickname = boardDto.getUserDto().getUsername();
        }

        BoardResponseDto boardResponseDto = new BoardResponseDto();
        boardResponseDto.setId(boardDto.getId());
        boardResponseDto.setBoardTitle(boardDto.getBoardTitle());
        boardResponseDto.setAuthorName(boardDto.getAuthorName());
        boardResponseDto.setBoardContent(boardDto.getBoardContent());
        boardResponseDto.setBoardHashtag(boardDto.getBoardHashtag());
        boardResponseDto.setCountBoardVisit(boardDto.getCountBoardVisit());
        boardResponseDto.setEmail(boardDto.getUserDto().getEmail());
        boardResponseDto.setNickname(getNickname);
        boardResponseDto.setCreatedAt(boardDto.getCreatedAt());

        return boardResponseDto;
    }
}
