package com.sparta.doing.dto;

import com.sparta.doing.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
public class LoginDto {
    @NonNull
    String username;
    @NonNull
    String password;

    @Builder
    public LoginDto(String username,
                    String password) {
        Assert.hasText(username, "username이 비어있습니다.");
        Assert.hasText(password, "password가 비어있습니다.");

        this.username = username;
        this.password = password;
    }

    public UserEntity toUserEntity(PasswordEncoder passwordEncoder) {
        return UserEntity.buildDefaultUser()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}