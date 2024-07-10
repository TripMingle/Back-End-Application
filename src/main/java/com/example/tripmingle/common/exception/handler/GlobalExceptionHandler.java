package com.example.tripmingle.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.tripmingle.common.error.ErrorResponse;
import com.example.tripmingle.common.exception.AlreadyExistsChatRoomException;
import com.example.tripmingle.common.exception.AlreadyExistsChatRoomUserException;
import com.example.tripmingle.common.exception.BoardBookMarkNotFoundException;
import com.example.tripmingle.common.exception.BoardCommentNotFoundException;
import com.example.tripmingle.common.exception.BoardLikesNotFoundException;
import com.example.tripmingle.common.exception.BoardNotFoundException;
import com.example.tripmingle.common.exception.BoardScheduleNotFoundException;
import com.example.tripmingle.common.exception.ChatRoomNotFoundException;
import com.example.tripmingle.common.exception.ChatRoomUserNotFoundException;
import com.example.tripmingle.common.exception.CompanionNotFound;
import com.example.tripmingle.common.exception.ContinentNotFoundException;
import com.example.tripmingle.common.exception.CountryImageNotFoundException;
import com.example.tripmingle.common.exception.CountryNotFoundException;
import com.example.tripmingle.common.exception.InvalidUserAccessException;
import com.example.tripmingle.common.exception.JsonParsingException;
import com.example.tripmingle.common.exception.LeaderCannotLeaveException;
import com.example.tripmingle.common.exception.LogoutUserException;
import com.example.tripmingle.common.exception.MatchingServerException;
import com.example.tripmingle.common.exception.OneOnOneChatRoomNotFoundException;
import com.example.tripmingle.common.exception.PostingCommentNotFoundException;
import com.example.tripmingle.common.exception.PostingLikesNotFoundException;
import com.example.tripmingle.common.exception.PostingNotFoundException;
import com.example.tripmingle.common.exception.RedisConnectException;
import com.example.tripmingle.common.exception.S3Exception;
import com.example.tripmingle.common.exception.TokenNotFoundException;
import com.example.tripmingle.common.exception.UserNotFoundException;
import com.example.tripmingle.common.exception.UserPersonalityNotFoundException;
import com.example.tripmingle.common.exception.UserScheduleNotFoundException;
import com.example.tripmingle.common.exception.UserTripNotFoundException;

import lombok.extern.slf4j.Slf4j;

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
	public ResponseEntity<ErrorResponse> handleJsonParsingException(JsonParsingException ex) {
		log.error("handleJsonParsingException", ex);
		final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}

	@ExceptionHandler(UserPersonalityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserPersonalityNotFoundException(UserPersonalityNotFoundException ex) {
		log.error("handleUserPersonalityNotFoundException", ex);
		final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}

	@ExceptionHandler(MatchingServerException.class)
	public ResponseEntity<ErrorResponse> handleMatchingServerException(MatchingServerException ex) {
		log.error("handleMatchingServerException", ex);
		final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}

	@ExceptionHandler(S3Exception.class)
	public ResponseEntity<ErrorResponse> handleS3Exception(S3Exception ex) {
		log.error("handleS3Exception", ex);
		final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}

	@ExceptionHandler(CountryNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCountryNotFoundException(CountryNotFoundException ex) {
		log.error("handleCountryNotFoundException", ex);
		final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}

	@ExceptionHandler(AlreadyExistsChatRoomException.class)
	public ResponseEntity<ErrorResponse> handleAlreadyExistsChatRoom(AlreadyExistsChatRoomException ex) {
		log.error("handleAlreadyExistsChatRoom", ex);
		final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}

	@ExceptionHandler(ChatRoomNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleChatRoomNotFound(ChatRoomNotFoundException ex) {
		log.error("handleChatRoomNotFound", ex);
		final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}

	@ExceptionHandler(OneOnOneChatRoomNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleOneOnOneChatRoomNotFound(OneOnOneChatRoomNotFoundException ex) {
		log.error("handleOneOnOneChatRoomNotFound", ex);
		final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}

	@ExceptionHandler(ChatRoomUserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleChatRoomUserNotFound(ChatRoomUserNotFoundException ex) {
		log.error("handleChatRoomUserNotFound", ex);
		final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}

	@ExceptionHandler(AlreadyExistsChatRoomUserException.class)
	public ResponseEntity<ErrorResponse> handleAlreadyExistsChatRoomUser(AlreadyExistsChatRoomUserException ex) {
		log.error("handleAlreadyExistsChatRoomUser", ex);
		final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}

	@ExceptionHandler(CountryImageNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCountryImageNotFoundException(CountryImageNotFoundException ex) {
		log.error("handleCountryImageNotFoundException", ex);
		final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}

	@ExceptionHandler(LogoutUserException.class)
	public ResponseEntity<ErrorResponse> handleLogoutUser(LogoutUserException ex) {
		log.error("handleLogoutUser", ex);
		final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}

	@ExceptionHandler(TokenNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleTokenNotFound(TokenNotFoundException ex) {
		log.error("handleTokenNotFound", ex);
		final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}

	@ExceptionHandler(ContinentNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleContinentNotFoundException(ContinentNotFoundException ex) {
		log.error("handleContinentNotFoundException", ex);
		final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}

}
