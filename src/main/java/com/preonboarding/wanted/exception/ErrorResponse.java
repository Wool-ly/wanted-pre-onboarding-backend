package com.preonboarding.wanted.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@RequiredArgsConstructor
public class ErrorResponse {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    public static ErrorResponse of(HttpStatus status, String message, HttpServletRequest request) {
        String timestamp = LocalDateTime.now().format(formatter);
        String path = request.getRequestURI();
        String error = status.getReasonPhrase();
        return new ErrorResponse(timestamp, status.value(), error, message, path);
    }

    public String convertToJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }


}
