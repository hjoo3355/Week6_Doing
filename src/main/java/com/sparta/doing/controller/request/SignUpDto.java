package com.sparta.doing.controller.request;

import com.sparta.doing.entity.Authority;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;

@Jacksonized
@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public class SignUpDto {
    @NotBlank
    String username;
    @NotBlank
    String password;
    @NotBlank
    String email;
    @NotBlank
    String nickname;
    Authority authority;
}
