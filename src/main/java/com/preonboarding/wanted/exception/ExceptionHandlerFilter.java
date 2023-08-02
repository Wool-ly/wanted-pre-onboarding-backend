package com.preonboarding.wanted.exception;

import com.preonboarding.wanted.exception.ErrorResponse;
import com.preonboarding.wanted.security.TokenNotValidateException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenNotValidateException ex) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, request, response, ex);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletRequest request,
            HttpServletResponse response, Throwable ex) throws IOException {

        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");

        response.getWriter().write(
                ErrorResponse.of(
                                HttpStatus.UNAUTHORIZED,
                                ex.getMessage(),
                                request
                        )
                        .convertToJson()
        );
    }


}
