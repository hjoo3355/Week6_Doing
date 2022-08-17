package com.sparta.doing.controller.requestdto;

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
