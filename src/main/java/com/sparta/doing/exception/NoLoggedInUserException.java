package com.sparta.doing.exception;

/**
 * 로그인한 유저가 없는데 해당 유저의 정보를 찾을 경우 발생
 */
public class NoLoggedInUserException extends RuntimeException {

    public NoLoggedInUserException(String message) {
        this(message, null);
    }

    public NoLoggedInUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
