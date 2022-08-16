package com.sparta.doing.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardDto {
    private Long id;
    private String boardTitle;
    private String authorName;
    private String boardContent;
//    private String boardThumbnail;
    private String boardHashtag;
    private String createdAt;
//    private Long boardLikeCount;
//    private Long countBoardVisit;
//    private Long countPost;
//    private List<postDTO> postList;
//    private UserResponseDto userResponseDto;
}
