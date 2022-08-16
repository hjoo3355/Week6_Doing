package com.sparta.doing.controller;

import com.sparta.doing.controller.response.UserResponseDto;
import com.sparta.doing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authtests")
public class TestController {
    private final UserService userService;

    // 내 정보 받기
    @GetMapping("/myinfo")
    public ResponseEntity<UserResponseDto> getMyInfo() {
        return ResponseEntity.ok(userService.getMyUserInfoWithAuthorities());
    }

    // 특정 유저 정보 받기
    @GetMapping("/userinfo/{username}")
    public ResponseEntity<?> getUserInfo(@PathVariable String username) {
        // if (SecurityUtil.getCurrentUsername().equals(username)) {
        //     return ResponseEntity.ok(userService.getUserInfo(username));
        // }
        return ResponseEntity.ok(userService.getUserInfo(username));
    }
}
