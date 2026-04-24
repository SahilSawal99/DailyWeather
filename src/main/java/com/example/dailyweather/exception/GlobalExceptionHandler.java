package com.example.dailyweather.exception;

import com.example.dailyweather.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientResponseException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalState(IllegalStateException ex, HttpServletRequest request) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }

    @ExceptionHandler(RestClientResponseException.class)
    public ResponseEntity<ErrorResponse> handleRestClientResponse(RestClientResponseException ex, HttpServletRequest request) {
        HttpStatusCode statusCode = ex.getStatusCode();

        // OpenWeather returns a helpful JSON body for 401/404; surface it (shortened) instead of a generic 500.
        String message = ex.getResponseBodyAsString();
        if (message != null) {
            message = message.trim();
        }
        if (message == null || message.isBlank()) {
            message = ex.getStatusText();
        }
        if (message == null || message.isBlank()) {
            message = "Upstream weather provider error";
        }
        if (message.length() > 500) {
            message = message.substring(0, 500);
        }
        return build(statusCode, message, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error", request);
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String message, HttpServletRequest request) {
        return build((HttpStatusCode) status, message, request);
    }

    private ResponseEntity<ErrorResponse> build(HttpStatusCode statusCode, String message, HttpServletRequest request) {
        int statusValue = statusCode.value();
        HttpStatus resolved = HttpStatus.resolve(statusValue);
        String error = resolved != null ? resolved.getReasonPhrase() : ("HTTP " + statusValue);

        ErrorResponse body = new ErrorResponse(
                Instant.now(),
                statusValue,
                error,
                message,
                request.getRequestURI()
        );
        return ResponseEntity.status(statusCode).body(body);
    }
}
