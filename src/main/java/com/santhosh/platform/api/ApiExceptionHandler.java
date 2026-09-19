package com.santhosh.platform.api;

import com.santhosh.platform.customer.CustomerNotFoundException;
import com.santhosh.platform.customer.DuplicateCustomerException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(CustomerNotFoundException.class)
    ResponseEntity<ApiError> notFound(CustomerNotFoundException ex, HttpServletRequest request) {
        return response(HttpStatus.NOT_FOUND, "CUSTOMER_NOT_FOUND", ex.getMessage(), request, Map.of());
    }
    @ExceptionHandler(DuplicateCustomerException.class)
    ResponseEntity<ApiError> duplicate(DuplicateCustomerException ex, HttpServletRequest request) {
        return response(HttpStatus.CONFLICT, "DUPLICATE_CUSTOMER", ex.getMessage(), request, Map.of());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> invalid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return response(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Request validation failed.", request, errors);
    }
    private ResponseEntity<ApiError> response(HttpStatus status, String code, String message,
                                               HttpServletRequest request, Map<String, String> fieldErrors) {
        return ResponseEntity.status(status).body(new ApiError(Instant.now(), status.value(), code,
                message, request.getRequestURI(), fieldErrors));
    }
}