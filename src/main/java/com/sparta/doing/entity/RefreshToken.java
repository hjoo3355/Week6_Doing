package com.sparta.doing.entity;

import com.sparta.doing.entity.TimeStamp;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Jacksonized
@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
@Table(name = "refresh_token")
@Entity
public class RefreshToken extends TimeStamp {

    @Id
    @Column(name = "rt_key")
    @NotBlank
    // username이 들어감
    String key;

    @With
    @Column(name = "rt_value")
    @NotBlank
    String value;

    protected RefreshToken() {
        this.key = null;
        this.value = null;
    }
}