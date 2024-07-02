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

    @ExceptionHandler(InvalidUserAccessException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUserAccess(InvalidUserAccessException ex) {
        log.error("handleInvalidUserAccess", ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(BoardBookMarkNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookMarkNotFoundException(PostingNotFoundException ex) {
        log.error("handleBookMarkNotFoundException", ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(BoardLikesNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBoardLikesNotFoundException(PostingNotFoundException ex) {
        log.error("handleBoardLikesNotFoundException", ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(PostingLikesNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostingLikesNotFoundException(PostingLikesNotFoundException ex) {
        log.error("handlePostingLikesNotFoundException", ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(CompanionNotFound.class)
    public ResponseEntity<ErrorResponse> handleCompanionNotFound(CompanionNotFound ex) {
        log.error("handleCompanionNotFound", ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(LeaderCannotLeaveException.class)
    public ResponseEntity<ErrorResponse> handleLeaderCannotLeaveException(LeaderCannotLeaveException ex) {
        log.error("handleCompanionNotFound", ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(BoardScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBoardScheduleNotFoundException(BoardScheduleNotFoundException ex) {
        log.error("handleBoardScheduleNotFoundException", ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(UserTripNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserTripNotFoundException(UserTripNotFoundException ex) {
        log.error("handleUserTripNotFoundException", ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(UserScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserScheduleNotFoundException(UserScheduleNotFoundException ex) {
        log.error("handleUserScheduleNotFoundException", ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(RedisConnectException.class)
    public ResponseEntity<ErrorResponse> handleRedisConnectException(RedisConnectException ex) {
        log.error("handleRedisConnectException", ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(JsonParsingException.class)
    public ResponseEntity<ErrorResponse> handleUserScheduleNotFoundException(JsonParsingException ex) {
        log.error("handleJsonParsingException", ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(UserPersonalityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserScheduleNotFoundException(UserPersonalityNotFoundException ex) {
        log.error("handleUserPersonalityNotFoundException", ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }


}
