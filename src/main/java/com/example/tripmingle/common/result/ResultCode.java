package com.example.tripmingle.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
	EXAMPLE(200, "EX001", "예시 결과코드입니다."),
	//board
	GET_BOARD_PREVIEW_SUCCESS(200, "B001", "게시물 미리보기 조회 성공"),
	GET_ALL_BOARD_SUCCESS(200, "B002", "모든 게시물 조회 성공"),
	GET_SINGLE_BOARD_SUCCESS(200, "B003", "단일 게시물 조회 성공"),
	CREATE_BOARD_SUCCESS(200, "B004", "게시물 작성 성공"),
	UPDATE_BOARD_SUCCESS(200, "B005", "게시물 수정 성공"),
	DELETE_BOARD_SUCCESS(200, "B006", "게시물 삭제 완료"),
	SEARCH_BOARD_SUCCESS(200, "B007", "게시물 검색 완료"),
	GET_MY_BOARD_SUCCESS(200, "B008", "내 게시물 조회 완료"),

	//boardComment
	CREATE_BOARD_COMMENT_SUCCESS(200, "BC001", "게시물 댓글 작성 성공"),
	UPDATE_BOARD_COMMENT_SUCCESS(200, "BC002", "게시물 댓글 수정 성공"),
	DELETE_BOARD_COMMENT_SUCCESS(200, "BC003", "게시물 댓글 삭제 완료"),

	//board bookmark
	TOGGLE_BOARD_BOOK_MARK_SUCCESS(200, "BM001", "북마크 상태변경 완료"),
	GET_MY_BOARD_BOOK_MARK_SUCCESS(200, "BM002", "북마크한 게시판 조회 성공"),

	//board likes
	TOGGLE_BOARD_LIKES_SUCCESS(200, "L001", "좋아요 상태변경 완료"),
	GET_MY_BOARD_LIKES_SUCCESS(200, "L002", "좋아요한 게시판 조회 성공"),

	//board schedule
	CREATE_BOARD_SCHEDULE_SUCCESS(200, "BS001", "게시판 일정 추가생성 성공"),
	MODIFY_BOARD_SCHEDULE_SUCCESS(200, "BS002", "게시판 일정 수정/삭제 성공"),
	GET_BOARD_SCHEDULE_SUCCESS(200, "BS003", "게시판 일정 조회 성공"),

	//user schedule
	CREATE_USER_TRIP_SUCCESS(200, "US001", "유저 여행 생성 성공"),
	CREATE_USER_SCHEDULE_SUCCESS(200, "US002", "유저 일정 생성 성공"),
	DELETE_USER_TRIP_SUCCESS(200, "US003", "유저 여행 삭제 성공"),
	GET_USER_TRIP_SUCCESS(200, "US004", "유저 여행 조회 성공"),
	GET_USER_SCHEDULE_SUCCESS(200, "US005", "유저 일정 조회 성공"),

	//companion
	CONFIRM_USERS_SUCCESS(200, "C001", "여행 확정에 성공"),
	LEAVE_COMPANION_SUCCESS(200, "C002", "여행동행 탈퇴에 성공"),
	GET_COMPANIONS_SUCCESS(200, "C003", "여행동행자 조회에 성공"),
	GET_MY_COMPANIONS_BOARDS_SUCCESS(200, "C004", "내 여행동행 게시물 조회 성공"),

	//matching
	GET_MY_USER_MATCHING_SUCCESS(200, "M001", "나와 어울리는 유저 조회 성공"),
	ADD_USER_PERSONALITY_SUCCESS(200, "M002", "유저 성향 등록 성공"),
	MODIFY_USER_PERSONALITY_SUCCESS(200, "M003", "유저 성향 변경 성공"),
	MATCHING_BOARD_SUCCESS(200, "M004", "보드 매칭 성공"),

	//OAuth
	OAUTH_LOGIN_SUCCESS(200, "O001", "소셜 로그인 성공"),
	OAUTH_TOKEN_ISSUE_SUCCESS(200, "O002", "소셜 로그인 토큰 발급 성공"),

	// posting
	CREATED_POSTING(200, "P001", "포스팅 작성 성공"),
	UPDATE_POSTING(200, "P002", "포스팅 수정 성공"),
	DELETE_POSTING(200, "P003", "포스팅 삭제 성공"),
	POSTING_PREVIEW_SUCCESS(200, "P004", "포스팅 미리보기 불러오기 성공"),
	GET_ONE_POSTING_SUCCESS(200, "P005", "포스팅 상세조회 성공"),
	GET_ALL_POSTINGS_SUCCESS(200, "P006", "전체 포스팅 조회 성공"),
	GET_SEARCH_POSTINGS_SUCCESS(200, "P007", "검색된 포스팅 조회 성공"),
	GET_ALL_LIKED_POSTING_SUCCESS(200, "P008", "좋아요 포스팅 전체 조회 성공"),
	GET_ALL_POPULARITY_POSTINGS_SUCCESS(200, "P009", "인기도 순 포스팅 조회 성공"),

	// posting comment
	POST_POSTING_COMMENT_SUCCESS(200, "PC001", "포스팅 댓글 달기 성공"),
	UPDATE_POSTING_COMMENT_SUCCESS(200, "PC002", "포스팅 댓글 수정 성공"),
	DELETE_POSTING_COMMENT_SUCCESS(200, "PC003", "포스팅 댓글 삭제 성공"),

	// posting likes
	TOGGLE_POSTING_LIKES_SUCCESS(200, "PL001", "포스팅 좋아요의 상태 변경 성공"),

	// auth
	VALIDATE_COMPLETE(200, "A001", "검증 성공"),
	LOGOUT_SUCCESS(200, "LO001", "로그아웃 성공"),
	ACCESS_TOKEN_ISSUE_SUCCESS(200, "A002", "액세스 토큰 발급 성공"),

	//country
	GET_COUNTRIES_BY_CONTINENT_SUCCESS(200, "C001", "해당 대륙에 존재하는 나라 조회 성공"),
	GET_COUNTRIES_BY_KEYWORD_SUCCESS(200, "C002", "검색어에 해당하는 나라 조회 성공"),
	UPLOAD_COUNTRY_IMAGE_SUCCESS(200, "C003", "나라 이미지 업로드 성공"),
	DELETE_COUNTRY_IMAGE_SUCCESS(200, "C004", "나라 이미지 삭제 성공"),
	UPLOAD_PRIMARY_COUNTRY_IMAGE_SUCCESS(200, "C005", "나라 대표이미지 업로드 성공"),
	GET_CONTINENT_SUCCESS(200, "C006", "대륙 조회 성공"),
	GET_COUNTRY_INFO_SUCCESS(200, "C007", "나라 정보 조회 성공"),

	// chat
	GET_ALL_CHAT_MESSAGES(200, "CM001", "전체 채팅 조회 성공"),

	// chat room
	CREATE_ONE_ON_ONE_CHAT_ROOM_SUCCESS(200, "O3CR001", "일대일 채팅방 생성 성공"),
	CREATE_GROUP_CHAT_ROOM_SUCCESS(200, "GCR001", "그룹 채팅방 생성 성공"),
	USER_ENTER_CHAT_ROOM_SUCCESS(200, "GCR002", "그룹 채팅방 참여 성공"),
	EXIT_GROUP_CHAT_ROOM_SUCCESS(200, "GCR003", "그룹 채팅방 퇴장 성공"),
	EXIT_ONE_ON_ONE_CHAT_ROOM_SUCCESS(200, "O3CR002", "일대일 채팅방 퇴장 성공"),

	// user
	UPDATE_USER_MY_PAGE_SUCCESS(200, "UMP001", "유저 마이페이지 수정 성공"),
	UPLOAD_USER_IMAGE_SUCCESS(200, "UMP002", "유저 이미지 업로드 성공"),
	UPDATE_USER_ADDITIONAL_INFO_SUCCESS(200, "UMP003", "유저 회원가입 시 추가정보 입력 성공"),
	;
	private final int status;
	private final String code;
	private final String message;
}
