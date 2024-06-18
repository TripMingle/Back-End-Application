package com.example.tripmingle.common.exception.handler;

import com.example.tripmingle.common.error.ErrorResponse;
import com.example.tripmingle.common.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBoardNotFoundException(BoardNotFoundException ex) {
        log.error("handleBoardNotFoundException", ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(BoardCommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBoardCommentNotFoundException(BoardCommentNotFoundException ex) {
        log.error("handleBoardCommentNotFoundException", ex);
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

    @ExceptionHandler(PostingCommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostingCommentNotFound(PostingCommentNotFoundException ex) {
        log.error("handlePostingCommentNotFoundException", ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(PostingCommentInvalidUserException.class)
    public ResponseEntity<ErrorResponse> handlePostingCommentInvalidUser(PostingCommentInvalidUserException ex) {
        log.error("handlePostingCommentInvalidUser", ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

}
