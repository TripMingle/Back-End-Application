package com.example.tripmingle.application.service;

import com.example.tripmingle.common.utils.CommonUtils;
import com.example.tripmingle.common.utils.UserUtils;
import com.example.tripmingle.dto.etc.UpdateBoardDTO;
import com.example.tripmingle.dto.req.board.CreateBoardReqDTO;
import com.example.tripmingle.dto.req.board.GetAllBoardReqDTO;
import com.example.tripmingle.dto.req.board.UpdateBoardReqDTO;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.BoardPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardPersistPort boardPersistPort;
    private final UserUtils userUtils;
    private final CommonUtils commonUtils;

    public List<Board> getRecentBoardsByCountryName(String countryName) {
        return boardPersistPort.getRecentBoardsByCountryName(countryName);
    }

    public void getBoardsByIdList() {
        boardPersistPort.getAllBoardsByIds();
    }

    public Page<Board> getAllBoards(GetAllBoardReqDTO getAllBoardReqDTO, Pageable pageable) {
        return boardPersistPort.getAllBoards(getAllBoardReqDTO.getCountryName()
                ,getAllBoardReqDTO.getGender()
                ,getAllBoardReqDTO.getLanguage()
                ,pageable);
    }

    public Board getBoardById(Long boardId) {

        return boardPersistPort.getBoardById(boardId);
    }

    public Board createBoard(CreateBoardReqDTO createBoardReqDTO, User currentUser) {

        Board board = Board.builder()
                .user(currentUser)
                .title(createBoardReqDTO.getTitle())
                .content(createBoardReqDTO.getContent())
                .continent(createBoardReqDTO.getContinent())
                .countryName(createBoardReqDTO.getCountryName())
                .personalType(commonUtils.convertListToString(createBoardReqDTO.getPersonalType()))
                .tripType(commonUtils.convertListToString(createBoardReqDTO.getTripType()))
                .currentCount(1)
                .maxCount(createBoardReqDTO.getMaxCount())
                .startDate(createBoardReqDTO.getStartDate())
                .endDate(createBoardReqDTO.getEndDate())
                .language(createBoardReqDTO.getLanguage())
                .commentCount(0)
                .build();

        return boardPersistPort.saveBoard(board);
    }


    public Board updateBoard(Long boardId, UpdateBoardReqDTO updateBoardReqDTO, User currentUser) {
        Board board = boardPersistPort.getBoardById(boardId);
        userUtils.validateMasterUser(board.getUser().getId(),currentUser.getId());
        UpdateBoardDTO updateBoardDTO = UpdateBoardDTO.builder()
                .continent(updateBoardReqDTO.getContinent())
                .countryName(updateBoardReqDTO.getCountryName())
                .startDate(updateBoardReqDTO.getStartDate())
                .endDate(updateBoardReqDTO.getEndDate())
                .language(updateBoardReqDTO.getLanguage())
                .maxCount(updateBoardReqDTO.getMaxCount())
                .tripType(commonUtils.convertListToString(updateBoardReqDTO.getTripType()))
                .personalType(commonUtils.convertListToString(updateBoardReqDTO.getPersonalType()))
                .title(updateBoardReqDTO.getTitle())
                .content(updateBoardReqDTO.getContent())
                .build();
        board.update(updateBoardDTO);
        return boardPersistPort.saveBoard(board);
    }

    public void deleteBoard(Long boardId, User currentUser) {
        Board board = boardPersistPort.getBoardById(boardId);
        userUtils.validateMasterUser(board.getUser().getId(),currentUser.getId());
        boardPersistPort.deleteBoardById(board.getId());
    }


    public Page<Board> searchBoard(String keyword, Pageable pageable) {
        return boardPersistPort.searchBoard(keyword, pageable);
    }

    public void getBoardsWithinRange() {
        boardPersistPort.getBoardsWithinRange();
    }

    public void saveBoard(Board board) {
        boardPersistPort.saveBoard(board);
    }

    public void updateCommentCount(Board board, int commentCount) {
        board.decreaseCommentCount(commentCount);
    }

    public Page<Board> getBoardsByUser(User user, Pageable pageable) {
        return boardPersistPort.getBoardByUser(user, pageable);
    }

}
