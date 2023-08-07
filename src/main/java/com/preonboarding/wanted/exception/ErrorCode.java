package com.preonboarding.wanted.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    /**
     * ******************************* Global Error CodeList ***************************************
     * HTTP Status Code
     * 400 : Bad Request
     * 401 : Unauthorized
     * 403 : Forbidden
     * 404 : Not Found
     * 500 : Internal Server Error
     * *********************************************************************************************
     */
    // 잘못된 서버 요청
    BAD_REQUEST_ERROR(400, "G001", "잘못된 요청입니다."),

    // @RequestBody 데이터 미 존재
    REQUEST_BODY_MISSING_ERROR(400, "G002", "Required request body 값이 누락되었습니다."),

    // 유효하지 않은 타입
    INVALID_TYPE_VALUE(400, "G003", "유효하지 않은 타입의 값입니다."),

    // Request Parameter 로 데이터가 전달되지 않을 경우
    MISSING_REQUEST_PARAMETER_ERROR(400, "G004", "RequestParameter 값이 누락되었습니다."),

    // 입력/출력 값이 유효하지 않음
    IO_ERROR(400, "G005", "입력/출력 값이 유효하지 않습니다."),

    // com.google.gson JSON 파싱 실패
    JSON_PARSE_ERROR(400, "G006", "JSON으로의 변환이 실패했습니다. "),

    // com.fasterxml.jackson.core Processing Error
    JACKSON_PROCESS_ERROR(400, "G007", "com.fasterxml.jackson.core 프로세스 오류입니다."),

    // 권한이 없음
    FORBIDDEN_ERROR(403, "G008", "권한이 없습니다."),

    // 서버로 요청한 리소스가 존재하지 않음
    NOT_FOUND_ERROR(404, "G009", "찾을 수 없는 리소스입니다."),

    // NULL Point Exception 발생
    NULL_POINT_ERROR(404, "G010", "입력값이 존재하지 않습니다."),

    // @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
    NOT_VALID_ERROR(404, "G011", "입력 값이 유효하지 않습니다."),

    // @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
    NOT_VALID_HEADER_ERROR(404, "G012", "Header의 데이터가 유효하지 않습니다."),

    // 서버가 처리 할 방법을 모르는 경우 발생
    INTERNAL_SERVER_ERROR(500, "G999", "서버 내부에서 오류가 발생했습니다."),

    NO_LOGIN(401, "G013", "로그인이 필요합니다."),
    NO_ADMIN(403, "G014", "권한이 없는 사용자입니다."),
    INPUT_VALUE_INVALID(400, "G015", "입력값이 올바르지 않습니다."),
    NO_USER(400, "G016", "존재하지 않는 사용자입니다."),
    ADMIN_TOKEN(400, "G017", "관리자 암호가 일치하지 않습니다."),
    SAME_EMAIL(400, "G018", "동일한 이메일이 존재합니다."),

    /**
     * ******************************* Custom Error CodeList ***************************************
     */
    // Transaction Insert Error
    INSERT_ERROR(200, "9999", "Insert 트랜잭션 오류입니다."),

    // Transaction Update Error
    UPDATE_ERROR(200, "9999", "Update 트랜잭션 오류입니다."),

    // Transaction Delete Error
    DELETE_ERROR(200, "9999", "Delete 트랜잭션 오류입니다."),

    ; // End

    /**
     * ******************************* Error Code Constructor ***************************************
     */
    // 에러 코드의 '코드 상태'을 반환한다.
    private int status;

    // 에러 코드의 '코드간 구분 값'을 반환한다.
    private String divisionCode;

    // 에러 코드의 '코드 메시지'을 반환한다.
    private String message;

    // 생성자 구성
    ErrorCode(int status, String divisionCode, String message) {
        this.status = status;
        this.divisionCode = divisionCode;
        this.message = message;
    }
}