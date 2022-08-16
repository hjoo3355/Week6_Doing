package com.sparta.doing.entity;

import com.sparta.doing.controller.request.SignUpDto;
import com.sparta.doing.util.UserFunction;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "userEntity",
        indexes = {
                @Index(columnList = "email", unique = true)
                // @Index(columnList = "createdAt"),
                // @Index(columnList = "createdBy")
        })
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
@Getter
public class UserEntity extends TimeStamp {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id = null;

    @NotNull
    @Column(unique = true, length = 50)
    String username;
    @NotNull
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boardList = new ArrayList<>();

    protected UserEntity() {
        this.username = null;
        this.password = null;
        this.email = null;
        this.nickname = null;
        this.authority = null;
    }

    @Builder(builderClassName = "buildDefaultUser",
            builderMethodName = "buildDefaultUser")
    public UserEntity(String username,
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

    public UserEntity of(SignUpDto signUpDto) {
        return UserEntity.buildDefaultUser()
                .username(signUpDto.getUsername())
                .password(signUpDto.getPassword())
                .email(signUpDto.getEmail())
                .nickname(signUpDto.getPassword())
                .authority(signUpDto.getAuthority())
                .build();
    }

    public void mapToBoard(Board board) { boardList.add(board); }

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
