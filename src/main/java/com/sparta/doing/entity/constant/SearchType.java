package com.sparta.doing.entity.constant;

import lombok.Getter;

public enum SearchType {
    ID("아이디"),
    TITLE("제목"),
    CONTENT("내용"),
    NICKNAME("닉네임"),
    HASHTAG("해시태그");

    @Getter
    private final String description;

    SearchType(String description) {
        this.description = description;
    }
}
