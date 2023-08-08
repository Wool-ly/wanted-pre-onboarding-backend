package com.preonboarding.wanted.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INPUT_VALUE_INVALID(400, "CM_001", "입력값이 올바르지 않습니다."),
    NO_USER(400, "CM_002", "존재하지 않는 사용자입니다."),
    ADMIN_TOKEN(400, "CM_003", "관리자 암호가 일치하지 않습니다."),
    SAME_EMAIL(400, "CM_004", "동일한 이메일이 존재합니다."),
    NO_LOGIN(401, "CM_005", "로그인이 필요합니다."),
    NO_ADMIN(403, "CM_006", "권한이 없는 사용자입니다."),
    METHOD_NOT_ALLOWED(405, "CM_007", "허용되지 않는 메서드입니다."),
    BAD_CREDENTIALS(400, "CM_008", "인증에 실패하였습니다."),
    ACCESS_DENIED(403, "CM_009", "접근이 거부되었습니다."),

    // 잘못된 서버 요청
    BAD_REQUEST_ERROR(400, "CM_010", "잘못된 요청입니다."),

    // @RequestBody 데이터 미 존재
    REQUEST_BODY_MISSING_ERROR(400, "CM_011", "Required request body 값이 누락되었습니다."),

    // 유효하지 않은 타입
    INVALID_TYPE_VALUE(400, "CM_012", "유효하지 않은 타입의 값입니다."),

    // Request Parameter 로 데이터가 전달되지 않을 경우
    MISSING_REQUEST_PARAMETER_ERROR(400, "CM_013", "RequestParameter 값이 누락되었습니다."),

    // 입력/출력 값이 유효하지 않음
    IO_ERROR(400, "CM_014", "입력/출력 값이 유효하지 않습니다."),

    // com.google.gson JSON 파싱 실패
    JSON_PARSE_ERROR(400, "CM_015", "JSON으로의 변환이 실패했습니다."),

    // com.fasterxml.jackson.core Processing Error
    JACKSON_PROCESS_ERROR(400, "CM_016", "com.fasterxml.jackson.core 프로세스 오류입니다."),

    // 권한이 없음
    FORBIDDEN_ERROR(403, "CM_017", "권한이 없습니다."),

    // 서버로 요청한 리소스가 존재하지 않음
    NOT_FOUND_ERROR(404, "CM_018", "찾을 수 없는 리소스입니다."),

    // NULL Point Exception 발생
    NULL_POINT_ERROR(404, "CM_019", "입력값이 존재하지 않습니다."),

    // @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
    NOT_VALID_ERROR(404, "CM_020", "입력 값이 유효하지 않습니다."),

    // @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
    NOT_VALID_HEADER_ERROR(404, "CM_021", "Header의 데이터가 유효하지 않습니다."),

    // 서버가 처리 할 방법을 모르는 경우 발생
    INTERNAL_SERVER_ERROR(500, "CM_022", "서버 내부에서 오류가 발생했습니다."),


    INVALID_PASSWORD(400, "CM_023", "비밀번호가 일치하지 않습니다."),

    // 게시글 관련
    POST_LIST_FETCH_ERROR(400, "CM_024", "게시글 목록 조회 중 오류가 발생했습니다."),
    POST_NOT_FOUND(400, "CM_025", "해당 게시글이 존재하지 않습니다."),
    UNAUTHORIZED_UPDATE(400, "CM_026", "게시글 수정 권한이 없습니다."),
    UNAUTHORIZED_DELETE(400, "CM_027", "게시글 삭제 권한이 없습니다."),
    EMPTY_POST_LIST(400, "CM_028", "게시글 목록이 비어 있습니다."),

    // JWT AUTH
    EXPIRED_TOKEN(401, "AUTH_001", "토큰이 만료되었습니다. 다시 로그인 해주세요."),
    INVALID_TOKEN(401, "AUTH_002", "유효하지 않은 토큰입니다."),
    MALFORMED_TOKEN(401, "AUTH_003", "형식이 잘못된 토큰입니다."),
    MISSING_TOKEN(401, "AUTH_004", "토큰이 누락되었습니다. 완전한 형태로 다시 입력해주세요."),
    REVOKED_TOKEN(401, "AUTH_005", "취소된 토큰입니다."),
    UNSUPPORTED_TOKEN(400, "AUTH_006", "지원되지 않는 형식의 토큰입니다."),
    TOKEN_NOT_FOUND(404, "AUTH_007", "토큰을 찾을 수 없습니다."),
    USER_AUTHENTICATION_FAILED(401, "AUTH_008", "사용자 인증에 실패하였습니다."),
    TOKEN_REFRESH_FAILED(400, "AUTH_009", "토큰 갱신에 실패하였습니다."),
    UNKNOWN_ERROR(400, "AUTH_010", "알 수 없는 오류입니다."),
    PERMISSION_DENIED(403, "AUTH_011", "접근 권한이 없습니다."),
    LOGIN_FAILED(401, "AUTH_008", "로그인에 실패하였습니다."),

    /**
     * ******************************* Custom Error CodeList
     * ***************************************
     */
    // Transaction Insert Error
    INSERT_ERROR(200, "9999", "Insert 트랜잭션 오류입니다."),

    // Transaction Update Error
    UPDATE_ERROR(200, "9999", "Update 트랜잭션 오류입니다."),

    // Transaction Delete Error
    DELETE_ERROR(200, "9999", "Delete 트랜잭션 오류입니다."); // End

    private int status;
    private final String code;
    private final String message;

}