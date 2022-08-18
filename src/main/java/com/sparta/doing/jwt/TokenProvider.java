package com.sparta.doing.jwt;

import com.sparta.doing.controller.responsedto.TokenDto;
import com.sparta.doing.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final String USER_ID = "uid";
    private final long ACCESS_TOKEN_LIFETIME_IN_MS;
    private final long REFRESH_TOKEN_LIFETIME_IN_MS;
    private final Key key;

    public long getACCESS_TOKEN_LIFETIME_IN_MS() {
        return ACCESS_TOKEN_LIFETIME_IN_MS;
    }

    public long getREFRESH_TOKEN_LIFETIME_IN_MS() {
        return REFRESH_TOKEN_LIFETIME_IN_MS;
    }

    // yml에 저장한 secret key와 토큰 지속시간 가져오기
    public TokenProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.access-token-lifetime-in-seconds}") long accessTokenLifetimeInSeconds,
            @Value("${jwt.refresh-token-lifetime-in-seconds}") long refreshTokenLifetimeInSeconds) {

        // second -> millisecond로 변환
        this.ACCESS_TOKEN_LIFETIME_IN_MS =
                accessTokenLifetimeInSeconds * 1000;
        this.REFRESH_TOKEN_LIFETIME_IN_MS =
                refreshTokenLifetimeInSeconds * 1000;

        // 시크릿키를 디코드하고
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        // 키의 자리수 검증 및 SecretKey 객체 생성
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰 생성
    public TokenDto createTokenDto(Authentication authentication, Long userId) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // 현재 시간(now)에 정해둔 유효 기간만큼 더해서 만료일(accessTokenExpiration) 설정
        long now = (new Date()).getTime();
        Date accessTokenExpiration = new Date(now + this.ACCESS_TOKEN_LIFETIME_IN_MS);
        Date refreshTokenExpiration =
                new Date(now + this.REFRESH_TOKEN_LIFETIME_IN_MS);

        // 액세스 토큰 생성
        var accessToken = Jwts.builder()
                // payload "sub": "name"
                .setSubject("Access Token")
                // 클레임에 userId(PK) 저장
                .claim(USER_ID, userId)
                // payload "auth": "ROLE_USER"
                .claim(AUTHORITIES_KEY, authorities)
                // payload "exp": accessTokenLifetimeInSeconds * 1000
                .setExpiration(accessTokenExpiration)
                // header "alg": "HS512"
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        // 리프레쉬 토큰 생성
        var refreshToken = Jwts.builder()
                .setSubject("Refresh Token")
                .setExpiration(refreshTokenExpiration)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenLifetime(this.ACCESS_TOKEN_LIFETIME_IN_MS)
                .refreshToken(refreshToken)
                .refreshTokenLifetime(this.REFRESH_TOKEN_LIFETIME_IN_MS)
                .build();
    }

    public TokenDto createEmptyTokenDto() {
        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken("logout")
                .accessTokenLifetime(1L)
                .refreshToken("logout")
                .refreshTokenLifetime(1L)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new InvalidKeyException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // User 객체를 생성해서 Authentication 반환
        long userId = (int) claims.get(USER_ID);
        CustomUserDetails principal =
                new CustomUserDetails(
                        String.valueOf(userId), "", String.valueOf(userId), authorities);

        return new UsernamePasswordAuthenticationToken(principal, accessToken, authorities);
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        return true;
    }

    // 토큰 복호화
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
