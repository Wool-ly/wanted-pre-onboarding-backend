package com.preonboarding.wanted.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorMessage; // 에러 메시지 추가

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // 부모 클래스 생성자 호출
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage(); // 에러 메시지 설정
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
