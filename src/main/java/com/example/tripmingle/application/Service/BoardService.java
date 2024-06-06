package com.example.tripmingle.application.Service;

import com.example.tripmingle.port.out.BoardPersistPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardService {
    private final BoardPersistPort boardPersistPort;

    public void getRecentBoards() {
        boardPersistPort.getRecentBoards();
    }

    public void getBoardsByIdList() {
        boardPersistPort.getAllBoardsByIds();
    }

    public void getAllBoards() {
        boardPersistPort.getAllBoards();
    }

    public void getBoardInfo() {
        boardPersistPort.getBoardById();
    }

    public void createBoard() {
        boardPersistPort.saveBoard();
    }

    public void updateBoard() {

    }

    public void deleteBoard() {

        boardPersistPort.deleteBoardById();
    }

    public void getBoardById() {
        boardPersistPort.getBoardById();
    }

    public void searchBoard() {
        boardPersistPort.searchBoard();
    }

    public void getBoardsWithinRange() {
        boardPersistPort.getBoardsWithinRange();
    }
}
