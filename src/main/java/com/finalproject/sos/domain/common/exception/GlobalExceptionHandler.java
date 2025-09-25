package com.finalproject.sos.domain.common.exception;


import jakarta.security.auth.message.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<Map<String, Object>> handleServerException(ServerException e) {

        return getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Map<String, Object>> handleAuthException(AuthException e) {

        return getErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameNotFoundException(UsernameNotFoundException e){

        return getErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }


    public ResponseEntity<Map<String, Object>> getErrorResponse(HttpStatus status, String message) {

        Map<String, Object> errorResponse = new HashMap<>();

        errorResponse.put("code", status.value());
        errorResponse.put("status", status.name());
        errorResponse.put("message", message);

        return new ResponseEntity<>(errorResponse, status);
    }
}
