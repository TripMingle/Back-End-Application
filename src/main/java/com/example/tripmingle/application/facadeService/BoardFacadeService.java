package com.example.tripmingle.application.facadeService;

import com.example.tripmingle.application.service.BoardCommentService;
import com.example.tripmingle.application.service.BoardService;
import com.example.tripmingle.dto.req.PostBoardReqDTO;
import com.example.tripmingle.dto.res.BoardCommentResDTO;
import com.example.tripmingle.dto.res.GetBoardInfoResDTO;
import com.example.tripmingle.dto.res.GetBoardsResDTO;
import com.example.tripmingle.dto.res.PostBoardResDTO;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.port.in.BoardCommentUseCase;
import com.example.tripmingle.port.in.BoardUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BoardFacadeService implements BoardUseCase, BoardCommentUseCase {
    private final BoardService boardService;
    private final BoardCommentService boardCommentService;

    @Override
    public List<GetBoardsResDTO> getRecentBoards(String countryName) {
        List<Board> boardList = boardService.getRecentBoards(countryName);

        return boardList
                .stream().map(board -> GetBoardsResDTO.builder()
                        .boardId(board.getId())
                        .title(board.getTitle())
                        .startDate(board.getStartDate())
                        .endDate(board.getEndDate())
                        .language(board.getLanguage())
                        .nickName(board.getUser().getNickName())
                        .ageRange(board.getUser().getAgeRange())
                        .gender(board.getUser().getGender())
                        .nationality(board.getUser().getNationality())
                        .maxCount(board.getMaxCount())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void getClusteredBoards() {
        boardService.getBoardsByIdList();
    }

    @Override
    public Page<GetBoardsResDTO> getAllBoards(String countryName, String gender, String language, Pageable pageable) {
        Page<Board> boardPage = boardService.getAllBoards(countryName, gender, language, pageable);

        return boardPage.map(board -> GetBoardsResDTO.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .startDate(board.getStartDate())
                .endDate(board.getEndDate())
                .language(board.getLanguage())
                .nickName(board.getUser().getNickName())
                .ageRange(board.getUser().getAgeRange())
                .gender(board.getUser().getGender())
                .nationality(board.getUser().getNationality())
                .maxCount(board.getMaxCount())
                .build());
    }

    @Override
    public GetBoardInfoResDTO getBoard(Long boardId) {
        Optional<Board> board = boardService.getBoardById(boardId);
        List<BoardCommentResDTO> boardComments = boardCommentService.getStructureBoardComment(board.get().getId());

        return GetBoardInfoResDTO.builder()
                .boardId(board.get().getId())
                .title(board.get().getTitle())
                .content(board.get().getContent())
                .language(board.get().getLanguage())
                .traits(board.get().getTraits())
                .types(board.get().getTypes())
                .startDate(board.get().getStartDate())
                .endDate(board.get().getEndDate())
                .currentCount(board.get().getCurrentCount())
                .maxCount(board.get().getMaxCount())
                .boardCommentResDTOS(boardComments)
                .userId(board.get().getUser().getId())
                .nickName(board.get().getUser().getNickName())
                .ageRange(board.get().getUser().getAgeRange())
                .gender(board.get().getUser().getGender())
                .nationality(board.get().getUser().getNationality())
                .selfIntroduction(board.get().getUser().getSelfIntroduction())
                .build();
    }

    @Override
    public PostBoardResDTO createBoard(PostBoardReqDTO postBoardReqDTO) {
        Long boardId = boardService.createBoard(postBoardReqDTO);
        return PostBoardResDTO.builder().boardId(boardId).build();
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
