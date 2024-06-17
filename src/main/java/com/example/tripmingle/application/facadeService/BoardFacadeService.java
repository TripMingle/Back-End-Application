package com.example.tripmingle.application.facadeService;

import com.example.tripmingle.application.service.BoardCommentService;
import com.example.tripmingle.application.service.BoardService;
import com.example.tripmingle.dto.req.CreateBoardCommentReqDTO;
import com.example.tripmingle.dto.req.CreateBoardReqDTO;
import com.example.tripmingle.dto.req.UpdateBoardCommentReqDTO;
import com.example.tripmingle.dto.req.UpdateBoardReqDTO;
import com.example.tripmingle.dto.res.*;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BoardComment;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.in.BoardCommentUseCase;
import com.example.tripmingle.port.in.BoardUseCase;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardFacadeService implements BoardUseCase, BoardCommentUseCase {
    private final BoardService boardService;
    private final BoardCommentService boardCommentService;
    private final UserPersistPort userPersistPort;

    @Override
    public List<GetBoardsResDTO> getRecentBoards(String countryName) {
        List<Board> boardList = boardService.getRecentBoards(countryName);
        User currentUser = userPersistPort.findCurrentUserByEmail();

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
                        .isMine(currentUser.getId().equals(board.getUser().getId()))
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
        User currentUser = userPersistPort.findCurrentUserByEmail();

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
                .isMine(currentUser.getId().equals(board.getUser().getId()))
                .build());
    }

    @Override
    public GetBoardInfoResDTO getBoard(Long boardId) {
        Board board = boardService.getBoardById(boardId);
        List<ParentBoardCommentResDTO> boardComments = boardCommentService.getStructureBoardComment(board.getId());
        User currentUser = userPersistPort.findCurrentUserByEmail();

        return GetBoardInfoResDTO.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .language(board.getLanguage())
                .traits(board.getTraits())
                .types(board.getTypes())
                .startDate(board.getStartDate())
                .endDate(board.getEndDate())
                .currentCount(board.getCurrentCount())
                .maxCount(board.getMaxCount())
                .createdAt(board.getCreatedAt())
                .isMine(currentUser.getId().equals(board.getUser().getId()))
                .boardCommentResDTOS(boardComments)
                .userId(board.getUser().getId())
                .nickName(board.getUser().getNickName())
                .ageRange(board.getUser().getAgeRange())
                .gender(board.getUser().getGender())
                .nationality(board.getUser().getNationality())
                .selfIntroduction(board.getUser().getSelfIntroduction())
                .build();
    }

    @Override
    public PostBoardResDTO createBoard(CreateBoardReqDTO createBoardReqDTO) {
        Long boardId = boardService.createBoard(createBoardReqDTO);
        return PostBoardResDTO.builder().boardId(boardId).build();
    }

    @Override
    public UpdateBoardResDTO updateBoard(Long boardId, UpdateBoardReqDTO patchBoardReqDTO) {
        //내가 쓴 글인지 맞는지 확인하는 로직
        Long resultBoardId = boardService.updateBoard(boardId, patchBoardReqDTO);
        return UpdateBoardResDTO.builder().boardId(resultBoardId)
                .build();
    }

    @Override
    public void deleteBoard(Long boardId) {
        //내가 쓴 글인지 맞는지 확인하는 로직
        //boardComment 삭제로직
        boardCommentService.deleteBoardCommentByBoardId(boardId);
        boardService.deleteBoard(boardId);
    }

    @Override
    public List<GetBoardsResDTO> searchBoard(String keyword) {
        List<Board> boardList = boardService.searchBoard(keyword);
        User currentUser = userPersistPort.findCurrentUserByEmail();
        return boardList.stream()
                .map(board -> GetBoardsResDTO.builder()
                        .boardId(board.getId())
                        .title(board.getTitle())
                        .startDate(board.getStartDate())
                        .endDate(board.getEndDate())
                        .maxCount(board.getMaxCount())
                        .language(board.getLanguage())
                        .nickName(board.getUser().getNickName())
                        .ageRange(board.getUser().getAgeRange())
                        .gender(board.getUser().getGender())
                        .nationality(board.getUser().getNationality())
                        .isMine(currentUser.getId().equals(board.getUser().getId()))
                        .build()).collect(Collectors.toList());
    }


    @Override
    public CreateBoardCommentResDTO createBoardComment(CreateBoardCommentReqDTO createBoardCommentReqDTO) {
        Board board = boardService.getBoardById(createBoardCommentReqDTO.getBoardId());
        BoardComment boardComment = boardCommentService.createBoardComment(createBoardCommentReqDTO, board);
        Long parentBoardCommentId = boardComment.isParentBoardCommentNull() ? -1 : boardComment.getParentBoardComment().getId();

        return CreateBoardCommentResDTO.builder()
                .parentBoardCommentId(parentBoardCommentId)
                .BoardCommentId(boardComment.getId())
                .content(boardComment.getContent())
                .userId(boardComment.getUser().getId())
                .userNickname(boardComment.getUser().getNickName())
                .createdAt(boardComment.getCreatedAt())
                .build();
    }

    @Override
    public void deleteBoardComment(Long commentId) {
        //내가 쓴 글인지 맞는지 확인하는 로직
        boardCommentService.deleteBoardComment(commentId);
    }

    @Override
    public UpdateBoardCommentResDTO updateBoardComment(UpdateBoardCommentReqDTO updateBoardCommentReqDTO, Long commentId) {
        //내가 단 댓글이 맞는지 확인하는 로직
        BoardComment boardComment = boardCommentService.updateBoardComment(updateBoardCommentReqDTO, commentId);
        return UpdateBoardCommentResDTO.builder().boardCommentId(boardComment.getId()).build();
    }

    @Override
    public void getBoardsWithinRange() {
        boardService.getBoardsWithinRange();
    }
}
