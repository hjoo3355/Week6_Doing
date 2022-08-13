package com.sparta.doing.dto;

import com.sparta.doing.entity.Authority;
import com.sparta.doing.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
public class SignUpDto {
    @NonNull
    String username;
    @NonNull
    String password;
    @NonNull
    String email;
    @NonNull
    String nickname;
    @NonNull
    Authority authority;

    @Builder
    public SignUpDto(String username,
                     String password,
                     String email,
                     String nickname,
                     Authority authority) {
        Assert.hasText(username, "username이 비어있습니다.");
        Assert.hasText(password, "password가 비어있습니다.");
        Assert.hasText(email, "email이 비어있습니다.");
        Assert.hasText(nickname, "nickname이 비어있습니다.");
        // Assert.hasText(authority.toString(), "authority가 비어있습니다.");

        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.authority = authority == null ? Authority.ROLE_USER : authority;
    }

    public UserEntity toUserEntity(PasswordEncoder passwordEncoder) {
        return UserEntity.buildDefaultUser()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .nickname(nickname)
                .authority(authority)
                .build();
    }
}
