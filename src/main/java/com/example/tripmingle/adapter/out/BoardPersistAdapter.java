package com.example.tripmingle.adapter.out;

import com.example.tripmingle.repository.BoardRepository;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.port.out.BoardPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BoardPersistAdapter implements BoardPersistPort {

    private final BoardRepository boardRepository;
    @Override
    public Page<Board> getAllBoards(String country, String gender, String language, Pageable pageable) {
        return boardRepository.findAllByCountryNameAndFilters(country,gender,language,pageable);
    }

    @Override
    public Optional<Board> getBoardById(Long boardId) {
        return boardRepository.findById(boardId);
    }

    @Override
    public void deleteBoardById() {

    }

    @Override
    public void getAllBoardsByIds() {

    }

    @Override
    public List<Board> getRecentBoards(String countryName) {
        return boardRepository.findRecentBoards(countryName);
    }

    @Override
    public Long saveBoard(Board board) {
        Board persistBoard = boardRepository.save(board);
        return persistBoard.getId();
    }

    @Override
    public void searchBoard() {

    }

    @Override
    public void getBoardsWithinRange() {

    }




}
