package com.example.tripmingle.port.out;

public interface BoardPersistPort {
    void getAllBoards();

    void getBoardById();

    void deleteBoardById();

    void getAllBoardsByIds();

    void getRecentBoards();

    void saveBoard();

    void searchBoard();

    void getBoardsWithinRange();
}
