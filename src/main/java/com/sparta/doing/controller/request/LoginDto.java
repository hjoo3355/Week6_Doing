package com.sparta.doing.controller.request;

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
public class LoginDto {
    @NotBlank
    String username;
    @NotBlank
    String password;
}
