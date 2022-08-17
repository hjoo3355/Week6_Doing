package com.sparta.doing.exception;

/**
 * DB에서 게시판 데이터를 찾을 수 없을 때 발생
 */
public class BoardNotFoundException extends RuntimeException {
    public BoardNotFoundException(String message) {
        super(message);
    }

    public BoardNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}