package com.example.tripmingle.application.service;

import com.example.tripmingle.common.utils.CommonUtils;
import com.example.tripmingle.dto.etc.UpdateBoardDTO;
import com.example.tripmingle.dto.req.CreateBoardReqDTO;
import com.example.tripmingle.dto.req.UpdateBoardReqDTO;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.BoardPersistPort;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        return boardPersistPort.getAllBoards(country, gender, language, pageable);
    }

    public Board getBoardById(Long boardId) {

        return boardPersistPort.getBoardById(boardId);
    }

    public Long createBoard(CreateBoardReqDTO createBoardReqDTO) {
        User currentUser = userPersistPort.findCurrentUserByEmail();

        Board board = Board.builder()
                .user(currentUser)
                .title(createBoardReqDTO.getTitle())
                .content(createBoardReqDTO.getContent())
                .continent(createBoardReqDTO.getContinent())
                .countryName(createBoardReqDTO.getCountryName())
                .traits(commonUtils.convertListToString(createBoardReqDTO.getTraits()))
                .types(commonUtils.convertListToString(createBoardReqDTO.getTypes()))
                .currentCount(1)
                .maxCount(createBoardReqDTO.getMaxCount())
                .startDate(createBoardReqDTO.getStartDate())
                .endDate(createBoardReqDTO.getEndDate())
                .language(createBoardReqDTO.getLanguage())
                .build();

        Long pk = boardPersistPort.saveBoard(board);
        return pk;
    }


    public Long updateBoard(Long boardId, UpdateBoardReqDTO updateBoardReqDTO) {
        Board board = boardPersistPort.getBoardById(boardId);
        UpdateBoardDTO updateBoardDTO = UpdateBoardDTO.builder()
                .continent(updateBoardReqDTO.getContinent())
                .countryName(updateBoardReqDTO.getCountryName())
                .startDate(updateBoardReqDTO.getStartDate())
                .endDate(updateBoardReqDTO.getEndDate())
                .language(updateBoardReqDTO.getLanguage())
                .maxCount(updateBoardReqDTO.getMaxCount())
                .types(commonUtils.convertListToString(updateBoardReqDTO.getTypes()))
                .traits(commonUtils.convertListToString(updateBoardReqDTO.getTraits()))
                .title(updateBoardReqDTO.getTitle())
                .content(updateBoardReqDTO.getContent())
                .build();
        board.update(updateBoardDTO);
        return boardPersistPort.saveBoard(board);
    }

    public void deleteBoard(Long boardId) {
        boardPersistPort.deleteBoardById(boardId);
    }


    public List<Board> searchBoard(String keyword) {
        return boardPersistPort.searchBoard(keyword);
    }

    public void getBoardsWithinRange() {
        boardPersistPort.getBoardsWithinRange();
    }
}
