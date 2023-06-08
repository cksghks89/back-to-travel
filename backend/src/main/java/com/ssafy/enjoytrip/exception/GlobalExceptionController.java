package com.ssafy.enjoytrip.exception;

import com.ssafy.enjoytrip.model.dto.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionController {

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<?> handleUnAuthorizeException(UnAuthorizedException e) {
        e.printStackTrace();

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.UNAUTHORIZED.value()).build();

        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception e) {
        e.printStackTrace();

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(e.getMessage())
                .status(500).build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
