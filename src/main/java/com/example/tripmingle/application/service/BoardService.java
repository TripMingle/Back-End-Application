package com.example.tripmingle.application.service;

import com.example.tripmingle.dto.req.PostBoardReqDTO;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.port.out.BoardPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BoardService {
    private final BoardPersistPort boardPersistPort;

    public List<Board> getRecentBoards(String countryName) {
        return boardPersistPort.getRecentBoards(countryName);
    }

    public void getBoardsByIdList() {
        boardPersistPort.getAllBoardsByIds();
    }

    public Page<Board> getAllBoards(String country, String gender, String language, Pageable pageable) {
        return boardPersistPort.getAllBoards(country, gender, language, pageable);
    }

    public Optional<Board> getBoardById(Long boardId) {
        return boardPersistPort.getBoardById(boardId);
    }

    public Long createBoard(PostBoardReqDTO postBoardReqDTO) {
        Board board = Board.builder().build();
        return boardPersistPort.saveBoard(board);
    }

    public void updateBoard() {

    }

    public void deleteBoard() {

        boardPersistPort.deleteBoardById();
    }


    public void searchBoard() {
        boardPersistPort.searchBoard();
    }

    public void getBoardsWithinRange() {
        boardPersistPort.getBoardsWithinRange();
    }

}
