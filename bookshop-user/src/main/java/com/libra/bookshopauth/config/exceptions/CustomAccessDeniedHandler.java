package com.libra.bookshopauth.config.exceptions;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setHeader("bookshop-reson-denied", "Authentication failed");
        // response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase()); // Use if you want to send 403 with default message
        int unauthorizedValue = HttpStatus.FORBIDDEN.value();
        LocalDateTime dateTime = LocalDateTime.now();
        String reasonPhrase = HttpStatus.FORBIDDEN.getReasonPhrase();
        String exceptionMessage = accessDeniedException != null && accessDeniedException.getMessage() != null ? accessDeniedException.getMessage() : "Authorisation failed";
        response.setStatus(unauthorizedValue); // Use if you want to send 403 with custom message

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
