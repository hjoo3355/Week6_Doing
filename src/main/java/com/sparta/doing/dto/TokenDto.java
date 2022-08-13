package com.sparta.doing.dto;

import lombok.Builder;
import lombok.Getter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Getter
public class TokenDto {

    private final String grantType;
    private final String accessToken;
    private final String refreshToken;
    private final Long accessTokenExpiresIn;
    private final String accessTokenExpireDate;
    private final Long refreshTokenExpiresIn;
    private final String refreshTokenExpireDate;

    private TokenDto() {
        this.grantType = null;
        this.accessToken = null;
        this.refreshToken = null;
        this.accessTokenExpiresIn = null;
        this.accessTokenExpireDate = null;
        this.refreshTokenExpiresIn = null;
        this.refreshTokenExpireDate = null;
    }

    @Builder
    public TokenDto(String grantType, String accessToken, String refreshToken
            , Long accessTokenExpiresIn, Long refreshTokenExpiresIn) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.accessTokenExpireDate = dateFormatter.format(accessTokenExpiresIn);
        this.refreshTokenExpireDate =
                dateFormatter.format(refreshTokenExpiresIn);
    }
}
