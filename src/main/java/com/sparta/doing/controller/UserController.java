package com.sparta.doing.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sparta.doing.controller.request.LoginDto;
import com.sparta.doing.controller.request.SignUpDto;
import com.sparta.doing.controller.request.TokenRequestDto;
import com.sparta.doing.controller.response.TokenDto;
import com.sparta.doing.controller.response.UserResponseDto;
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
}
