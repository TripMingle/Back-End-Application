package com.example.tripmingle.common.exception.handler;

import com.example.tripmingle.common.error.ErrorResponse;
import com.example.tripmingle.common.exception.ExampleException;
import com.example.tripmingle.common.exception.PostingNotFoundException;
import com.example.tripmingle.common.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ExampleException.class)
    public ResponseEntity<ErrorResponse> handleExampleException(ExampleException ex) {
        log.error("handleExampleException", ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        log.error("handleUserNotFoundException", ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(PostingNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostingNotFound(PostingNotFoundException ex) {
        log.error("handlePostingNotFoundException", ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

}
