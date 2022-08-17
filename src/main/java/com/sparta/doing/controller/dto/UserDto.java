package com.sparta.doing.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.doing.entity.Authority;
import com.sparta.doing.entity.Board;
import com.sparta.doing.entity.UserEntity;
import lombok.*;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String nickname;
    Authority authority;
    private String createdAt;
    private String modifiedAt;

    public static UserDto of(Long id, String username, String password, String email, String nickname, Authority authority) {
        return new UserDto(id, username, password, email, nickname, authority, null, null);
    }

    public static UserDto of(Long id, String username, String password, String email, String nickname, Authority authority, String createdAt, String modifiedAt) {
        return new UserDto(id, username, password, email, nickname, authority, createdAt, modifiedAt);
    }

    public static UserDto from(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setPassword(userEntity.getPassword());
        userDto.setEmail(userEntity.getEmail());
        userDto.setNickname(userEntity.getNickname());
        userDto.setAuthority(userEntity.getAuthority());
        userDto.setCreatedAt(userEntity.getCreatedAt());
        userDto.setModifiedAt(userEntity.getModifiedAt());

        return userDto;
    }

    public UserEntity toEntity() {
        return UserEntity.of(id, username, password, email, nickname, authority);
    }
}
