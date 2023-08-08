package com.preonboarding.wanted.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.preonboarding.wanted.exception.ErrorCode;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        String exception = (String) request.getAttribute("exception");
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken == null || bearerToken.trim().isEmpty() || bearerToken.equals("Bearer")
                || bearerToken.equals("Bearer ")) {
            log.info("WRONG_BEARER_TOKEN");
            setResponse(response, ErrorCode.MISSING_TOKEN);
        } else if (exception == null) {
            log.info("UNKNOWN_ERROR");
            setResponse(response, ErrorCode.UNKNOWN_ERROR);
        }
        //잘못된 타입의 토큰인 경우
        else if (exception.equals(ErrorCode.MALFORMED_TOKEN.getCode())) {
            log.info("WRONG_TYPE_TOKEN");
            setResponse(response, ErrorCode.MALFORMED_TOKEN);
        }
        //토큰 만료된 경우
        else if (exception.equals(ErrorCode.EXPIRED_TOKEN.getCode())) {
            log.info("EXPIRED_TOKEN");
            setResponse(response, ErrorCode.EXPIRED_TOKEN);
        }
        //지원되지 않는 토큰인 경우
        else if (exception.equals(ErrorCode.UNSUPPORTED_TOKEN.getCode())) {
            log.info("UNSUPPORTED_TOKEN");
            setResponse(response, ErrorCode.UNSUPPORTED_TOKEN);
        } else {
            log.info("ACCESS_DENIED");
            setResponse(response, ErrorCode.ACCESS_DENIED);
        }
    }

    //한글 출력을 위해 getWriter() 사용
    private void setResponse(HttpServletResponse response, ErrorCode exceptionCode)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseJson = objectMapper.createObjectNode();

        responseJson.put("status", exceptionCode.getStatus());
        responseJson.put("code", exceptionCode.getCode());
        responseJson.put("message", exceptionCode.getMessage());

        response.getWriter().print(responseJson);
    }
}