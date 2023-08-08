package com.preonboarding.wanted.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.wanted.exception.ErrorCode;
import com.preonboarding.wanted.exception.ErrorResponse;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } catch (JwtException ex) {
            String message = ex.getMessage();
            if(ErrorCode.UNKNOWN_ERROR.getMessage().equals(message)) {
                log.info("UNKNOWN_ERROR", ex);
                setResponse(response, ErrorCode.UNKNOWN_ERROR);
            }
            //잘못된 타입의 토큰인 경우
            else if(ErrorCode.MALFORMED_TOKEN.getMessage().equals(message)) {
                log.info("WRONG_TYPE_TOKEN", ex);
                setResponse(response, ErrorCode.MALFORMED_TOKEN);
            }
            //토큰 만료된 경우
            else if(ErrorCode.EXPIRED_TOKEN.getMessage().equals(message)) {
                log.info("EXPIRED_TOKEN", ex);
                setResponse(response, ErrorCode.EXPIRED_TOKEN);
            }
            //지원되지 않는 토큰인 경우
            else if(ErrorCode.UNSUPPORTED_TOKEN.getMessage().equals(message)) {
                log.info("UNSUPPORTED_TOKEN", ex);
                setResponse(response, ErrorCode.UNSUPPORTED_TOKEN);
            }
            else if(ErrorCode.INTERNAL_SERVER_ERROR.getMessage().equals(message)) {
                log.info("INTERNAL_SERVER_ERROR", ex);
                setResponse(response, ErrorCode.INTERNAL_SERVER_ERROR);
            }
            else {
                log.info("ACCESS_DENIED", ex);
                setResponse(response, ErrorCode.ACCESS_DENIED);
            }

        }
    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws RuntimeException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.getStatus());

        ErrorResponse errorResponse =
                new ErrorResponse(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);

        response.getWriter().print(jsonResponse);

    }
}