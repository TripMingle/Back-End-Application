package com.example.tripmingle.adapter.in;

import com.example.tripmingle.port.in.BoardUseCase;
import com.example.tripmingle.port.in.BoardCommentUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardController {
    private final BoardUseCase boardUseCase;
    private final BoardCommentUseCase boardCommentUseCase;

    //게시물 미리보기 리스트(나라명 입력, 최신순 n개)
    public void getRecentBoards(){
        boardUseCase.getRecentBoards();
    }

    //게시물 전체조회(나라명 입력, 성별이나 언어로 필터링, 페이징)
    public void getAllBoards(){
        boardUseCase.getAllBoards();
    }

    //단일게시물 조회 (상세내용 + 댓글까지 + 유저정보 + 태그)
    public void getBoard(){
        boardUseCase.getBoard();
    }

    //게시글 작성
    public void createBoard(){
        boardUseCase.createBoard();
    }

    //게시글 수정
    public void updateBoard(){
        boardUseCase.updateBoard();
    }

    //게시글 삭제
    public void deleteBoard(){
        boardUseCase.deleteBoard();
    }

    //게시글 검색
    public void searchBoard(){
        boardUseCase.searchBoard();
    }

    //댓글달기 (대댓글달기도 같은 API 사용)
    public void createComment(){
        boardCommentUseCase.createBoardComment();
    }

    //댓글수정(대댓글도 같은 API 사용)
    public void  updateComment(){
        boardCommentUseCase.updateBoardComment();
    }

    //댓글삭제(status만 변경, 대댓글도 사용가능)
    public void deleteComment(){
        boardCommentUseCase.deleteBoardComment();
    }

    //북마크

    //좋아요

    //여행확정짓기

    //내가 방장인 모든 게시물 조회

    //내가 참여하고있는 모든 게시물 조회



}
