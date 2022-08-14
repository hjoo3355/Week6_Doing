package com.sparta.doing.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "email", unique = true),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class UserAccount extends TimeStamp {
    @Id
    @Column(length = 50)
    private String userId;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @Column(length = 100)
    private String email;

    @Setter
    @Column(length = 50)
    private String nickname;

    protected UserAccount() {}

    private UserAccount(String userId, String password, String email, String nickname) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }

    public static UserAccount of(String userId, String password, String email, String nickname) {
        return new UserAccount(userId, password, email, nickname);
    }

    @Override
    public boolean equals(Object object) {
        UserAccount userAccount = new UserAccount();
        if(this == object) return true;
        if(!(userAccount.getClass().isInstance(object))) return false;
        return userId != null && userId.equals(userAccount.getUserId());
    }

    @Override
    public int hashCode(){ return Objects.hash(userId); }
}
