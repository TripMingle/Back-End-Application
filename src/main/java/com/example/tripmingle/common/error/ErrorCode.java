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
    BOARD_LIKES_NOT_FOUND(400,"BL001", "좋아요를 찾을 수 없습니다."),

    //Companion
    COMPANION_NOT_FOUND(400,"C001","여행동행을 찾을 수 없습니다."),
    LEADER_CANT_LEAVE(400,"C002","팀장은 여행동행을 떠날 수 없습니다."),

    //Board_Schedule
    BOARD_SCHEDULE_NOT_FOUND(400,"BS001","여행 일정을 찾을 수 없습니다."),

    //OAuth
    KAKAO_BAD_REQUEST(400, "O001", "카카오 소셜 로그인에 잘못된 요청입니다."),
    KAKAO_ALREADY_EXISTS_USER(400, "O002", "이미 트립밍글에 존재하는 카카오 유저입니다."),
    KAKAO_NO_EXISTS_USER(400, "O003", "트립밍글에 존재하지 않는 카카오 유저입니다."),
    KAKAO_SERVER_ERROR(500, "O002", "카카오 소셜 로그인 서버 오류입니다."),

    // user
    USER_NOT_FOUND(404, "U001", "해당 유저가 존재하지 않습니다."),
    INVALID_USER_ACCESS(403, "U002", "해당 유저에 권한이 없습니다."),

    // posting
    POSING_NOT_FOUND(404, "P001", "해당 포스팅글이 존재하지 않습니다."),

    // posting comment
    POSTING_COMMENT_NOT_FOUND(404, "PC001", "해당 댓글이 존재하지 않습니다."),

    // posting likes
    POSTING_LIKES_NOT_FOUND(404, "PL001", "해당 좋아요를 찾을 수 없습니다."),
    ;

    final private int status;
    final private String ErrorCode;
    final private String message;
}
