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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {
    private final UserService userService;

    @GetMapping("/api/user")
    public String userInfo(@PathVariable Integer id, Model model) {
        User userEntity = userService.회원정보(id);
        model.addAttribute("user", userEntity);
        return "user/updateForm";
    }
}