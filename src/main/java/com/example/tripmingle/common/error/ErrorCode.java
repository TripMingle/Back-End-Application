package com.example.tripmingle.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	//Example
	EXAMPLE(400, "EX001", "예시 에러코드입니다."),
	//Board
	BOARD_NOT_FOUND(400, "B001", "게시물을 찾을 수 없습니다."),

	//BoardComment
	BOARD_COMMENT_NOT_FOUND(400, "BC001", "게시물 댓글을 찾을 수 없습니다."),

	//Board BookMark
	BOARD_BOOK_MARK_NOT_FOUND(400, "BM001", "북마크를 찾을 수 없습니다."),

	//Board Likes
	BOARD_LIKES_NOT_FOUND(400, "BL001", "좋아요를 찾을 수 없습니다."),

	//Companion
	COMPANION_NOT_FOUND(400, "C001", "여행동행을 찾을 수 없습니다."),
	LEADER_CANT_LEAVE(400, "C002", "팀장은 여행동행을 떠날 수 없습니다."),

	//Board_Schedule
	BOARD_SCHEDULE_NOT_FOUND(400, "BS001", "여행 일정을 찾을 수 없습니다."),

	//User trip & user schedule
	USER_TRIP_NOT_FOUND(400, "US001", "유저 여행일정을 찾을 수 없습니다."),
	USER_SCHEDULE_NOT_FOUND(400, "US002", "유저 일정을 찾을 수 없습니다."),

	//Matching
	USER_PERSONALITY_NOT_FOUND(400, "M001", "유저 성향이 저장되어있지 않습니다."),
	MATCHING_SERVER_EXCEPTION(500, "M002", "내부 오류로 실패하였습니다."),

	//country
	COUNTRY_NOT_FOUND(400, "CO_001", "잘못된 나라 이름입니다."),
	COUNTRY_IMAGE_NOT_FOUND(400, "CO_002", "나라 이미지를 찾을 수 없습니다."),
	CONTINENT_NOT_FOUND(400, "CO_003", "대륙을 찾을 수 없습니다."),

	//Json parsing
	JSON_PARSE_EXCEPTION(400, "J001", "json 파싱에실패하였습니다."),

	//Redis
	REDIS_CONNECT_EXCEPTION(400, "R001", "redisConnection 에 예외가 발생하였습니다."),

	// auth
	LOGOUT_USER(400, "A001", "로그아웃된 유저입니다."),
	TOKEN_NOT_FOUND(404, "A002", "토큰이 없습니다."),
	INVALID_REFRESH_TOKEN(400, "A003", "리프레시 토큰이 유효하지 않습니다."),

	//S3
	EMPTY_FILE_EXCEPTION(400, "S001", "파일이 존재하지 않습니다."),
	IO_EXCEPTION_ON_IMAGE_UPLOAD(400, "S002", "파일 업로드 오류가 발생하였습니다."),
	NO_FILE_EXTENTION(400, "S003", "잘못된 파일입니다."),
	INVALID_FILE_EXTENTION(400, "S004", "잘못된 파일 확장자입니다."),
	PUT_OBJECT_EXCEPTION(400, "S005", "파일 업로드에 실패하였습니다."),
	IO_EXCEPTION_ON_IMAGE_DELETE(400, "S006", "이미지 삭제에 실패하였습니다."),

	//OAuth
	KAKAO_BAD_REQUEST(400, "O001", "카카오 소셜 로그인에 잘못된 요청입니다."),
	KAKAO_ALREADY_EXISTS_USER(400, "O002", "이미 트립밍글에 존재하는 카카오 유저입니다."),
	KAKAO_NO_EXISTS_USER(400, "O003", "트립밍글에 존재하지 않는 카카오 유저입니다."),
	KAKAO_SERVER_ERROR(500, "O002", "카카오 소셜 로그인 서버 오류입니다."),

	// user
	USER_NOT_FOUND(404, "U001", "해당 유저가 존재하지 않습니다."),
	INVALID_USER_ACCESS(403, "U002", "해당 유저에 권한이 없습니다."),
	ALREADY_EXISTS_USER_NICKNAME(403, "U003", "이미 존재하는 닉네임입니다."),

	// posting
	POSING_NOT_FOUND(404, "P001", "해당 포스팅글이 존재하지 않습니다."),

	// posting comment
	POSTING_COMMENT_NOT_FOUND(404, "PC001", "해당 댓글이 존재하지 않습니다."),
	INVALID_POSTING_COMMENT_ACCESS(400, "PC002", "해당 댓글은 해당 포스팅에 달 수 없습니다."),

	// posting likes
	POSTING_LIKES_NOT_FOUND(404, "PL001", "해당 좋아요를 찾을 수 없습니다."),

	// chat
	ALREADY_EXISTS_CHAT_ROOM(400, "CR002", "해당 채팅방은 이미 존재합니다."),
	INVALID_CHAT_ROOM_ID_FOR_CHAT_MESSAGE(403, "CM001", "해당 채팅에 대한 채팅방 아이디값이 유효하지 않습니다."),

	// chatroom
	CHATROOM_NOT_FOUND(404, "CR001", "해당 채팅방을 찾을 수 없습니다."),
	ONE_ON_ONE_CHAT_ROOM_NOT_FOUND(404, "O3CR001", "해당 일대일 채팅방을 찾을 수 없습니다."),

	// chat room user
	USER_IN_CHAT_ROOM_NOT_FOUND(404, "CRU001", "해당 채팅방에 유저를 찾을 수 없습니다."),
	ALREADY_EXISTS_USER_IN_CHAT_ROOM(400, "CRU002", "해당 채팅방에 이미 유저가 존재합니다."),
	;

	final private int status;
	final private String ErrorCode;
	final private String message;
}
