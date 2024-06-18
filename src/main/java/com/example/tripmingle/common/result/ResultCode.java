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

    //OAuth
    OAUTH_LOGIN_SUCCESS(200, "O001", "소셜 로그인이 성공하였습니다."),
    OAUTH_TOKEN_ISSUE_SUCCESS(200, "O002", "소셜 로그인 토큰 발급이 성공하였습니다."),

    // posting
    CREATED_POSTING(201, "P001", "포스팅이 작성되었습니다."),
    UPDATE_POSTING(202, "P002", "포스팅이 수정되었습니다."),
    DELETE_POSTING(203, "P003", "포스팅이 삭제되었습니다."),
    POSTING_PREVIEW_SUCCESS(204, "P004", "포스팅 미리보기 불러오기 성공했습니다."),
    GET_ONE_POSTING_SUCCESS(205, "P005", "포스팅 상세조회 성공했습니다."),
    GET_ALL_POSTINGS_SUCCESS(206, "P006", "전체 포스팅 조회가 성공했습니다."),
    GET_SEARCH_POSTINGS_SUCCESS(207, "P007", "검색된 포스팅 조회가 성공했습니다."),

    // posting comment
    POST_POSTING_COMMENT_SUCCESS(208, "PC001", "댓글 달기가 성공했습니다."),
    UPDATE_POSTING_COMMENT_SUCCESS(209, "PC002", "댓글 수정이 성공했습니다."),
    DELETE_POSTING_COMMENT_SUCCESS(210, "PC003", "댓글 삭제가 성공했습니다."),

    // auth
    VALIDATE_COMPLETE(200, "A001", "검증이 완료되었습니다."),


    //language
    GET_COUNTRIES_BY_CONTINENT_SUCCESS(200, "L001", "해당 대륙에 존재하는 나라 조회에 성공하였습니다."),
    GET_COUNTRIES_BY_KEYWORD_SUCCESS(200, "L002", "검색어에 해당하는 나라 조회에 성공하였습니다.");
    private final int status;
    private final String code;
    private final String message;
}
