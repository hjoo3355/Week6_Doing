package com.sparta.doing.util;

public class UserFunction {
    /**
     * 실행중인 클래스 이름을 반환
     *
     * @return 클래스명
     */
    public static String getClassName() {
        return Thread.currentThread().getStackTrace()[2].getClassName() + ": ";
    }

    /**
     * 실행중인 함수 이름을 반환
     *
     * @return 함수명
     */
    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName() + ": ";
    }
}