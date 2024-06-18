package com.example.tripmingle.application.facadeService;

import com.example.tripmingle.application.service.BoardCommentService;
import com.example.tripmingle.application.service.BoardLikesService;
import com.example.tripmingle.application.service.BoardService;
import com.example.tripmingle.application.service.BoardBookMarkService;
import com.example.tripmingle.common.utils.CommonUtils;
import com.example.tripmingle.dto.req.CreateBoardCommentReqDTO;
import com.example.tripmingle.dto.req.CreateBoardReqDTO;
import com.example.tripmingle.dto.req.UpdateBoardCommentReqDTO;
import com.example.tripmingle.dto.req.UpdateBoardReqDTO;
import com.example.tripmingle.dto.res.*;
import com.example.tripmingle.entity.*;
import com.example.tripmingle.port.in.BoardCommentUseCase;
import com.example.tripmingle.port.in.BoardUseCase;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardFacadeService implements BoardUseCase, BoardCommentUseCase {
    private final BoardService boardService;
    private final BoardCommentService boardCommentService;
    private final UserPersistPort userPersistPort;
    private final CommonUtils commonUtils;
    private final BoardBookMarkService boardBookMarkService;
    private final BoardLikesService boardLikesService;

    @Override
    public List<GetBoardsResDTO> getRecentBoards(String countryName) {
        List<Board> boardList = boardService.getRecentBoardsByCountryName(countryName);
        User currentUser = userPersistPort.findCurrentUserByEmail();


        return boardList
                .stream().map(board -> GetBoardsResDTO.builder()
                        .boardId(board.getId())
                        .title(board.getTitle())
                        .startDate(board.getStartDate())
                        .endDate(board.getEndDate())
                        .language(board.getLanguage())
                        .commentCount(board.getCommentCount())
                        .nickName(board.getUser().getNickName())
                        .ageRange(board.getUser().getAgeRange())
                        .gender(board.getUser().getGender())
                        .nationality(board.getUser().getNationality())
                        .currentCount(board.getCurrentCount())
                        .maxCount(board.getMaxCount())
                        .isMine(currentUser.getId().equals(board.getUser().getId()))
                        .isLiked(boardLikesService.isLikedBoard(currentUser,board))
                        .isBookMarked(boardBookMarkService.isBookMarkedBoard(currentUser,board))
                        .build())
                .collect(Collectors.toList());
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
                .commentCount(board.getCommentCount())
                .nickName(board.getUser().getNickName())
                .ageRange(board.getUser().getAgeRange())
                .gender(board.getUser().getGender())
                .nationality(board.getUser().getNationality())
                .currentCount(board.getCurrentCount())
                .maxCount(board.getMaxCount())
                .isMine(currentUser.getId().equals(board.getUser().getId()))
                .isLiked(boardLikesService.isLikedBoard(currentUser,board))
                .isBookMarked(boardBookMarkService.isBookMarkedBoard(currentUser,board))
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
                .traits(commonUtils.convertStringToArray(board.getTraits()))
                .types(commonUtils.convertStringToArray(board.getTypes()))
                .startDate(board.getStartDate())
                .endDate(board.getEndDate())
                .currentCount(board.getCurrentCount())
                .maxCount(board.getMaxCount())
                .createdAt(board.getCreatedAt())
                .commentCount(board.getCommentCount())
                .isMine(currentUser.getId().equals(board.getUser().getId()))
                .isLiked(boardLikesService.isLikedBoard(currentUser,board))
                .isBookMarked(boardBookMarkService.isBookMarkedBoard(currentUser,board))
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
    @Transactional(readOnly = false)
    public PostBoardResDTO createBoard(CreateBoardReqDTO createBoardReqDTO) {
        Long boardId = boardService.createBoard(createBoardReqDTO);
        return PostBoardResDTO.builder().boardId(boardId).build();
    }

    @Override
    @Transactional(readOnly = false)
    public UpdateBoardResDTO updateBoard(Long boardId, UpdateBoardReqDTO patchBoardReqDTO) {
        //내가 쓴 글인지 맞는지 확인하는 로직
        Long resultBoardId = boardService.updateBoard(boardId, patchBoardReqDTO);
        return UpdateBoardResDTO.builder().boardId(resultBoardId)
                .build();
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteBoard(Long boardId) {
        //내가 쓴 글인지 맞는지 확인하는 로직
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
                        .currentCount(board.getCurrentCount())
                        .maxCount(board.getMaxCount())
                        .language(board.getLanguage())
                        .nickName(board.getUser().getNickName())
                        .ageRange(board.getUser().getAgeRange())
                        .gender(board.getUser().getGender())
                        .nationality(board.getUser().getNationality())
                        .commentCount(board.getCommentCount())
                        .isMine(currentUser.getId().equals(board.getUser().getId()))
                        .isLiked(boardLikesService.isLikedBoard(currentUser,board))
                        .isBookMarked(boardBookMarkService.isBookMarkedBoard(currentUser,board))
                        .build()).collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = false)
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
    @Transactional(readOnly = false)
    public void deleteBoardComment(Long commentId) {
        //내가 쓴 글인지 맞는지 확인하는 로직
        Board board = boardCommentService.getBoardByCommentId(commentId);
        int commentCount = boardCommentService.deleteBoardComment(commentId);
        boardService.updateCommentCount(board, -commentCount);

    }

    @Override
    @Transactional(readOnly = false)
    public UpdateBoardCommentResDTO updateBoardComment(UpdateBoardCommentReqDTO updateBoardCommentReqDTO, Long commentId) {
        //내가 단 댓글이 맞는지 확인하는 로직
        BoardComment boardComment = boardCommentService.updateBoardComment(updateBoardCommentReqDTO, commentId);
        return UpdateBoardCommentResDTO.builder().boardCommentId(boardComment.getId()).build();
    }

    @Override
    public void getBoardsWithinRange() {
        boardService.getBoardsWithinRange();
    }

    @Override
    @Transactional(readOnly = false)
    public ToggleStateResDTO toggleBoardBookMark(Long boardId) {
        Board board = boardService.getBoardById(boardId);
        boolean state = boardBookMarkService.toggleBoardBookMark(board);
        return ToggleStateResDTO.builder()
                .state(state)
                .build();
    }

    @Override
    public List<GetBoardsResDTO> getMyBookMarkedBoards() {
        User currentUser = userPersistPort.findCurrentUserByEmail();
        List<BoardBookMark> boardBookMarks = boardBookMarkService.getMyBookMarkedBoards(currentUser);

        return boardBookMarks.stream()
                .map(boardBookmark-> GetBoardsResDTO
                        .builder()
                        .boardId(boardBookmark.getBoard().getId())
                        .title(boardBookmark.getBoard().getTitle())
                        .startDate(boardBookmark.getBoard().getStartDate())
                        .endDate(boardBookmark.getBoard().getEndDate())
                        .currentCount(boardBookmark.getBoard().getCurrentCount())
                        .maxCount(boardBookmark.getBoard().getMaxCount())
                        .language(boardBookmark.getBoard().getLanguage())
                        .commentCount(boardBookmark.getBoard().getCommentCount())
                        .nickName(boardBookmark.getBoard().getUser().getNickName())
                        .ageRange(boardBookmark.getBoard().getUser().getAgeRange())
                        .gender(boardBookmark.getBoard().getUser().getGender())
                        .nationality(boardBookmark.getBoard().getUser().getNationality())
                        .isMine(currentUser.getId().equals(boardBookmark.getBoard().getUser().getId()))
                        .isLiked(boardLikesService.isLikedBoard(currentUser, boardBookmark.getBoard()))
                        .isBookMarked(boardBookMarkService.isBookMarkedBoard(currentUser,boardBookmark.getBoard()))
                        .build()).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = false)
    public ToggleStateResDTO toggleBoardLikes(Long boardId) {
        Board board = boardService.getBoardById(boardId);
        boolean state = boardLikesService.toggleBoardLikes(board);
        return ToggleStateResDTO.builder()
                .state(state)
                .build();
    }

    @Override
    public List<GetBoardsResDTO> getMyLikedBoards() {
        User currentUser = userPersistPort.findCurrentUserByEmail();
        List<BoardLikes> boardLikes = boardLikesService.getMyLikedBoards(currentUser);

        return boardLikes.stream()
                .map(boardLike-> GetBoardsResDTO
                        .builder()
                        .boardId(boardLike.getBoard().getId())
                        .title(boardLike.getBoard().getTitle())
                        .startDate(boardLike.getBoard().getStartDate())
                        .endDate(boardLike.getBoard().getEndDate())
                        .currentCount(boardLike.getBoard().getCurrentCount())
                        .maxCount(boardLike.getBoard().getMaxCount())
                        .language(boardLike.getBoard().getLanguage())
                        .commentCount(boardLike.getBoard().getCommentCount())
                        .nickName(boardLike.getBoard().getUser().getNickName())
                        .ageRange(boardLike.getBoard().getUser().getAgeRange())
                        .gender(boardLike.getBoard().getUser().getGender())
                        .nationality(boardLike.getBoard().getUser().getNationality())
                        .isMine(currentUser.getId().equals(boardLike.getBoard().getUser().getId()))
                        .isLiked(boardLikesService.isLikedBoard(currentUser,boardLike.getBoard()))
                        .isBookMarked(boardBookMarkService.isBookMarkedBoard(currentUser,boardLike.getBoard()))
                        .build()).collect(Collectors.toList());
    }

    @Override
    public void getClusteredBoards() {
        boardService.getBoardsByIdList();
    }
}
