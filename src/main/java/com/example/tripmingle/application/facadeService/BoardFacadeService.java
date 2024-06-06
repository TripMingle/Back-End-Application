package com.example.tripmingle.application.facadeService;

import com.example.tripmingle.port.in.BoardUseCase;
import com.example.tripmingle.port.in.BoardCommentUseCase;
import com.example.tripmingle.application.Service.BoardService;
import com.example.tripmingle.application.Service.BoardCommentService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardFacadeService implements BoardUseCase, BoardCommentUseCase {
    private final BoardService boardService;
    private final BoardCommentService boardCommentService;

    @Override
    public void getRecentBoards() {
        boardService.getRecentBoards();
    }

    @Override
    public void getClusteredBoards() {
        boardService.getBoardsByIdList();
    }

    @Override
    public void getAllBoards() {
        boardService.getAllBoards();
    }

    @Override
    public void getBoard() {
        boardService.getBoardInfo();
        boardCommentService.getBoardCommentsByBoardId();

    }

    @Override
    public void createBoard() {
        boardService.createBoard();
    }

    @Override
    public void updateBoard() {
        boardService.updateBoard();
    }

    @Override
    public void deleteBoard() {
        boardService.deleteBoard();
    }

    @Override
    public void searchBoard() {
        boardService.searchBoard();
    }

    @Override
    public void getBoardsWithinRange() {
        boardService.getBoardsWithinRange();
    }

    @Override
    public void createBoardComment() {
        boardCommentService.createBoardComment();
    }

    @Override
    public void deleteBoardComment() {
        boardCommentService.getBoardCommentById();
        boardCommentService.deleteBoardComment();
    }

    @Override
    public void updateBoardComment() {
        boardCommentService.updateBoardComment();
    }
}
