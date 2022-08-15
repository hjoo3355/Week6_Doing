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
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    // 회원가입 요청
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(userService.signup(signUpDto));
    }

//    이광훈 팀원의 코드입니다. 참고하시면 좋겠습니다.
//    @PostMapping("/signup")
////    @ResponseStatus(value = HttpStatus.CREATED)
//    public Response<UserAccountRequestDto> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
//        UserAccount userAccount = userService.signup(signupRequestDto);
////        return Response.getData("회원가입 되었습니다.", UserAccountRequestDto.builder()
////                .userId(userAccount.getUserId())
////                .build());
//        return Response.getData("회원가입 되었습니다.",
//                UserAccountRequestDto.builder()
//                        .userId(userAccount.getUserId())
//                        .build());
//    }

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

//    이광훈 팀원의 코드입니다. 참고하시면 좋겠습니다.
//    @PostMapping("/login")
//    public ResponseEntity<Response<JwtTokenDto>> login(@RequestBody @Valid LoginRequestDto loginRequestDto,
//                                                       HttpServletResponse httpServletResponse) {
//        if(this.userService.login(loginRequestDto)){
//            UsernamePasswordAuthenticationToken authenticationToken =
//                    new UsernamePasswordAuthenticationToken(loginRequestDto.getUserId(), loginRequestDto.getPassword());
//
//            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            String jwtToken = jwtTokenProvider.createToken(authentication);
//
//            Cookie cookie = new Cookie("cookie", jwtToken);
//            cookie.setHttpOnly(true);
//            cookie.setMaxAge(300);
//            httpServletResponse.addCookie(cookie);
//
//            HttpHeaders httpHeaders = new HttpHeaders();
//
//            Response<JwtTokenDto> response =
//                    Response.getData("로그인 되었습니다.", JwtTokenDto.builder().jwtToken(jwtToken).build());
//
//            return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
//        } else {
//            Response<JwtTokenDto> response = Response.getData("로그인 실패했습니다. 아이디 또는 비밀번호를 다시 입력해주세요."
//                    , JwtTokenDto.builder().jwtToken("").build());
//            HttpHeaders httpHeaders = new HttpHeaders();
//            return new ResponseEntity<>(response, httpHeaders, HttpStatus.UNAUTHORIZED);
//        }
//    }

    // 회원 로그인 페이지
    @GetMapping("/loginview")
    public String login() {
        return "login";
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok(userService.logout());
    }

//    이광훈 팀원의 코드입니다. 참고하시면 좋겠습니다.
//    @GetMapping("/logout")
//    public ResponseEntity<Response<String>> logout() {
//        HttpHeaders httpHeaders = new HttpHeaders();
//
//        Response<String> response = Response.getData("로그아웃 되었습니다.", null);
//
//        return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
//    }
//}

    // 토큰 재발급
    @PostMapping("/renew")
    public ResponseEntity<TokenDto> renewToken(@RequestBody TokenRequestDto tokenRequestDto)
            throws JWTVerificationException, RefreshTokenNotFoundException {
        return ResponseEntity.ok(userService.renewToken(tokenRequestDto));
    }
}
