package com.sparta.doing.dto;

import com.sparta.doing.entity.Authority;
import com.sparta.doing.entity.UserEntity;
import com.sparta.doing.util.UserFunction;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.util.Assert;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
public class UserResponseDto {
    @NonNull
    String username;
    @NonNull
    String email;
    @NonNull
    String nickname;
    @NonNull
    Authority authority;

    @Builder
    public UserResponseDto(String username,
                           String email,
                           String nickname,
                           Authority authority) {
        Assert.hasText(username,
                UserFunction.getClassName() + "username이 비어있습니다.");
        Assert.hasText(email,
                UserFunction.getClassName() + "email이 비어있습니다.");
        Assert.hasText(nickname,
                UserFunction.getClassName() + "nickname이 비어있습니다.");
        Assert.hasText(authority.toString(),
                UserFunction.getClassName() + "authority가 비어있습니다.");

        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.authority = authority;
    }

    public static UserResponseDto of(UserEntity userEntity) {
        Assert.notNull(userEntity,
                UserFunction.getClassName() + "userEntity가 비어있습니다.");
        return UserResponseDto.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .nickname(userEntity.getNickname())
                .authority(userEntity.getAuthority())
                .build();
    }
}
