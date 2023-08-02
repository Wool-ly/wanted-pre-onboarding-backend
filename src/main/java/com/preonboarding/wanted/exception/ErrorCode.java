package com.preonboarding.wanted.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INPUT_VALUE_INVALID("CM_001", "입력값이 올바르지 않습니다.", 400),
    NO_USER("CM_002", "존재하지 않는 사용자입니다.", 400),
    ADMIN_TOKEN("CM_003", "관리자 암호가 일치하지 않습니다.", 400),
    SAME_EMAIL("CM_004", "동일한 이메일이 존재합니다.", 400),
    NO_LOGIN("CM_005", "로그인이 필요합니다.", 401),
    NO_ADMIN("CM_006", "권한이 없는 사용자입니다.", 403),
    SERVER_ERROR("CM_007", "서버 내부에서 오류가 발생했습니다.", 500);

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(String code, String message, int status){
        this.code = code;
        this.message = message;
        this.status = status;
    }

}