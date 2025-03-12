package com.libra.bookshopauth.config.exceptions;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("bookshop-reson-error", "Authentication failed");
        // response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase()); // Use if you want to send 401 with default message
        int unauthorizedValue = HttpStatus.UNAUTHORIZED.value();
        LocalDateTime dateTime = LocalDateTime.now();
        String reasonPhrase = HttpStatus.UNAUTHORIZED.getReasonPhrase();
        String exceptionMessage = authException != null && authException.getMessage() != null ? authException.getMessage() : "Authentication failed";
        response.setStatus(unauthorizedValue); // Use if you want to send 401 with custom message

        // Send custom JSON response
        response.setContentType("application/json;charset=UTF-8");
        String jsonResponse = String.format(
                "{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\"}",
                dateTime,
                unauthorizedValue,
                reasonPhrase,
                exceptionMessage,
                request.getRequestURI());
        response.getWriter().write(jsonResponse);
    }
}
