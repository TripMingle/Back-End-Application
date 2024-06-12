package com.example.tripmingle.application.Service;

import com.example.tripmingle.common.utils.CommonUtils;
import com.example.tripmingle.dto.req.PostBoardReqDTO;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.BoardPersistPort;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BoardService {
    private final BoardPersistPort boardPersistPort;
    private final UserPersistPort userPersistPort;
    private final CommonUtils commonUtils;

    public List<Board> getRecentBoards(String countryName) {
        return boardPersistPort.getRecentBoards(countryName);
    }

    public void getBoardsByIdList() {
        boardPersistPort.getAllBoardsByIds();
    }

    public Page<Board> getAllBoards(String country, String gender, String language, Pageable pageable) {
        return boardPersistPort.getAllBoards(country,gender,language,pageable);
    }

    public Optional<Board> getBoardById(Long boardId) {

        return boardPersistPort.getBoardById(boardId);
    }

    public Long createBoard(PostBoardReqDTO postBoardReqDTO) {
        Optional<User> currentUser = userPersistPort.getCurrentUser();

        Board board = Board.builder()
                .user(currentUser.get())
                .title(postBoardReqDTO.getTitle())
                .content(postBoardReqDTO.getContent())
                .continent(postBoardReqDTO.getContinent())
                .countryName(postBoardReqDTO.getCountryName())
                .traits(commonUtils.convertListToString(postBoardReqDTO.getTraits()))
                .types(commonUtils.convertListToString(postBoardReqDTO.getTypes()))
                .currentCount(1)
                .maxCount(postBoardReqDTO.getMaxCount())
                .startDate(postBoardReqDTO.getStartDate())
                .endDate(postBoardReqDTO.getEndDate())
                .language(postBoardReqDTO.getLanguage())
                .build();

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
