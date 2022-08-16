package com.sparta.doing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.doing.controller.request.SignUpDto;
import com.sparta.doing.domain.TimeStamp;
import com.sparta.doing.util.UserFunction;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users",
        indexes = {
                @Index(columnList = "email", unique = true)
                // @Index(columnList = "createdAt"),
                // @Index(columnList = "createdBy")
        })
// @JsonPropertyOrder({"id", "username", "password", "email", "nickname",
//         "authority", "createdAt", "modifiedAt"})
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
@Getter
public class UserEntity extends TimeStamp {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id = null;

    @NotNull
    @Column(unique = true, length = 50)
    String username;
    @NotNull
    @JsonIgnore
    String password;
    @NotNull
    @Column(unique = true, length = 100)
    String email;
    @NotNull
    @Column(unique = true, length = 50)
    String nickname;
    @NotNull
    @Enumerated(EnumType.STRING)
    Authority authority;

    protected UserEntity() {
        this.username = null;
        this.password = null;
        this.email = null;
        this.nickname = null;
        this.authority = null;
    }

    @Builder(builderClassName = "buildDefaultUser",
            builderMethodName = "buildDefaultUser")
    protected UserEntity(String username,
                         String password,
                         String email,
                         String nickname,
                         Authority authority) {
        Assert.hasText(username, UserFunction.getClassName() +
                "username이 비어있습니다.");
        Assert.hasText(password, UserFunction.getClassName() + "password가 비어있습니다.");
        Assert.hasText(email, UserFunction.getClassName() + "email이 비어있습니다.");
        Assert.hasText(nickname, UserFunction.getClassName() + "nickname이 비어있습니다.");
        Assert.hasText(authority.toString(), UserFunction.getClassName() + "authority가 비어있습니다.");

        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.authority = authority;
    }

    public static UserEntity of(SignUpDto signUpDto, PasswordEncoder passwordEncoder) {
        return UserEntity.buildDefaultUser()
                .username(signUpDto.getUsername())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .email(signUpDto.getEmail())
                .nickname(signUpDto.getNickname())
                .authority(signUpDto.getAuthority() == null ?
                        Authority.ROLE_USER : signUpDto.getAuthority())
                .build();
    }

    @Override
    public boolean equals(Object object) {
        UserEntity userEntity = new UserEntity();
        if (this == object) return true;
        if (!(userEntity.getClass().isInstance(object))) return false;
        return username != null && username.equals(userEntity.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
