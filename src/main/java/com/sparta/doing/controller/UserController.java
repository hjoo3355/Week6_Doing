package com.sparta.doing.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sparta.doing.dto.*;
import com.sparta.doing.exception.RefreshTokenNotFoundException;
import com.sparta.doing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    // 회원가입 요청
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(userService.signup(signUpDto));
    }

    // 회원 가입 페이지
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    // 로그인 요청
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) throws BadCredentialsException {
        return ResponseEntity.ok(userService.login(loginDto));
    }

    // 회원 로그인 페이지
    @GetMapping("/loginView")
    public String login() {
        return "login";
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok(userService.logout());
    }

    // 토큰 재발급
    @PostMapping("/renew")
    public ResponseEntity<TokenDto> renewToken(@RequestBody TokenRequestDto tokenRequestDto)
            throws JWTVerificationException, RefreshTokenNotFoundException {
        return ResponseEntity.ok(userService.renewToken(tokenRequestDto));
    }

    // // 내 정보
    // @GetMapping("/myInfo")
    // public ResponseEntity<UserResponseDto> getMyUserInfo() {
    //     return ResponseEntity.ok(userService.getMyUserInfoWithAuthorities());
    // }

    // // 특정 유저 정보
    // @Secured("ROLE_ADMIN") // <- 접근 권한 샘플 코드
    // @GetMapping("/{username}")
    // public ResponseEntity<UserResponseDto> getUserInfo(@PathVariable String username) {
    //     return ResponseEntity.ok(userService.getUserInfo(username));
    // }

}
