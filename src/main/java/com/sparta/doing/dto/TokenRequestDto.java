package com.sparta.doing.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenRequestDto {
    private final String accessToken;
    private final String refreshToken;

    private TokenRequestDto() {
        this.accessToken = null;
        this.refreshToken = null;
    }

    @Builder
    public TokenRequestDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
