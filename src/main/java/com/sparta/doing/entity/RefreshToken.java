package com.sparta.doing.entity;

import io.jsonwebtoken.lang.Assert;
import lombok.Builder;
import lombok.Getter;
import lombok.With;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Table(name = "refresh_token")
@Entity
public class RefreshToken extends BaseTimeEntity {

    @Id
    @Column(name = "rt_key")
    // username이 들어감
    private final String key;

    @With
    @Column(name = "rt_value")
    private final String value;

    protected RefreshToken() {
        this.key = null;
        this.value = null;
    }

    @Builder
    public RefreshToken(String key, String value) {
        Assert.hasText(key, RefreshToken.class.getName() + ".key가 비어있습니다.");
        Assert.hasText(value, RefreshToken.class.getName() + ".value가 비어있습니다.");
        this.key = key;
        this.value = value;
    }
}