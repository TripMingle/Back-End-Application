package com.example.tripmingle.port.in;

public interface BoardUseCase {
    void getRecentBoards();

    void getClusteredBoards();

    void getAllBoards();

    void getBoard();

    void createBoard();

    void updateBoard();

    void deleteBoard();

    void searchBoard();

    void getBoardsWithinRange();
}
