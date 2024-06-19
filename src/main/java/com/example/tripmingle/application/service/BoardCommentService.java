package com.example.tripmingle.application.service;

import com.example.tripmingle.dto.etc.ChildBoardCommentDTO;
import com.example.tripmingle.dto.req.CreateBoardCommentReqDTO;
import com.example.tripmingle.dto.req.UpdateBoardCommentReqDTO;
import com.example.tripmingle.dto.res.ParentBoardCommentResDTO;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BoardComment;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.BoardCommentPersistPort;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardCommentService {
    private final BoardCommentPersistPort boardCommentPersistPort;
    private final UserPersistPort userPersistPort;

    //추후 부모댓글이 없어질경우 등을 고려하여 리팩터링 필요
    public List<ParentBoardCommentResDTO> getStructureBoardComment(Long boardId) {
        List<BoardComment> boardComments = boardCommentPersistPort.getBoardCommentsByBoardId(boardId);
        User currentUser = userPersistPort.findCurrentUserByEmail();

        // 댓글이 시간순으로 정렬되어 있다고 가정합니다.
        Map<Long, List<BoardComment>> commentMap = new HashMap<>();
        List<BoardComment> parentList = new ArrayList<>();

        // 부모 댓글과 자식 댓글 분류
        boardComments.forEach(comment -> {
            if (comment.isParentBoardCommentNull()) {
                commentMap.put(comment.getId(), new ArrayList<>());
                parentList.add(comment);
            } else {
                Long parentId = comment.getParentBoardComment().getId();
                commentMap.get(parentId).add(comment);
            }
        });

        // 부모 댓글을 DTO로 변환하고 자식 댓글을 포함시키기
        return parentList.stream()
                .map(parent -> {
                    Long parentId = parent.getId();
                    ParentBoardCommentResDTO parentDTO = getParentBoardCommentInfo(parent, currentUser);
                    List<ChildBoardCommentDTO> childDTOs = commentMap.getOrDefault(parentId, Collections.emptyList()).stream()
                            .map(child -> getChildBoardCommentInfo(child, parentId, currentUser))
                            .collect(Collectors.toList());
                    parentDTO.setChildBoards(childDTOs);
                    return parentDTO;
                })
                .collect(Collectors.toList());

        /*
        Map<Long, List<BoardComment>> commentMap = new HashMap<>();
        List<BoardComment> parentList = new ArrayList<>();


        for (BoardComment boardComment : boardComments) {
            Long commentId = boardComment.getId();

            if (boardComment.isParentBoardCommentNull()) {
                commentMap.put(commentId, new ArrayList<>());
                parentList.add(boardComment);
            } else {
                Long parentId = boardComment.getParentBoardComment().getId();
                commentMap.get(parentId).add(boardComment);
            }
        }

        List<ParentBoardCommentResDTO> boardCommentResDTOS = new ArrayList<>();

        for (BoardComment parentBoardComment : parentList) {
            Long parentId = parentBoardComment.getId();
            ParentBoardCommentResDTO parentBoardCommentResDTO = getParentBoardCommentInfo(parentBoardComment, currentUser);
            List<ChildBoardCommentDTO> childBoardCommentDTOS = new ArrayList<>();

            for (BoardComment childBoardComment : commentMap.get(parentId)) {
                childBoardCommentDTOS.add(getChildBoardCommentInfo(childBoardComment, parentId, currentUser));
            }

            parentBoardCommentResDTO.setChildBoards(childBoardCommentDTOS);
            boardCommentResDTOS.add(parentBoardCommentResDTO);
        }

        return boardCommentResDTOS;

         */
    }

    private ParentBoardCommentResDTO getParentBoardCommentInfo(BoardComment boardComment, User currentUser) {

        return ParentBoardCommentResDTO.builder()
                .boardId(boardComment.getBoard().getId())
                .boardCommentId(boardComment.getId())
                .content(boardComment.getContent())
                .registeredDate(boardComment.getCreatedAt())
                .userId(boardComment.getUser().getId())
                .userNickname(boardComment.getUser().getNickName())
                .isMine(currentUser.getId().equals(boardComment.getUser().getId()))
                .build();
    }

    private ChildBoardCommentDTO getChildBoardCommentInfo(BoardComment boardComment, Long parentId, User currentUser) {

        return ChildBoardCommentDTO.builder()
                .boardId(boardComment.getBoard().getId())
                .boardCommentId(boardComment.getId())
                .parentId(parentId)
                .content(boardComment.getContent())
                .registeredDate(boardComment.getCreatedAt())
                .userId(boardComment.getUser().getId())
                .userNickname(boardComment.getUser().getNickName())
                .isMine(currentUser.getId().equals(boardComment.getUser().getId()))
                .build();
    }

    public BoardComment createBoardComment(CreateBoardCommentReqDTO createBoardCommentReqDTO, Board board) {
        BoardComment parentBoardComment;
        User user = userPersistPort.findCurrentUserByEmail();
        if (isParent(createBoardCommentReqDTO.getParentBoardCommentId())) {
            parentBoardComment = null;
        } else {
            parentBoardComment = boardCommentPersistPort
                    .getBoardCommentById(createBoardCommentReqDTO.getParentBoardCommentId());
        }

        board.updateCommentCount(1);

        return boardCommentPersistPort.saveBoardComment(BoardComment.builder()
                .parentBoardComment(parentBoardComment)
                .user(user)
                .board(board)
                .content(createBoardCommentReqDTO.getContent())
                .build());
    }

    private boolean isParent(Long id) {
        if (id == -1) return true;
        else return false;
    }

    public int deleteBoardComment(Long commentId) {
        int commentCount = 0;
        BoardComment boardComment = boardCommentPersistPort.getBoardCommentById(commentId);
        commentCount++;
        if (boardComment.isParentBoardCommentNull()) {
            List<BoardComment> childBoardComments = boardCommentPersistPort.getBoardCommentByParentBoardId(boardComment.getId());
            commentCount += childBoardComments.size();

            childBoardComments.stream()
                    .forEach(childComment -> boardCommentPersistPort.deleteBoardComment(childComment));

        }
        boardCommentPersistPort.deleteBoardComment(boardComment);

        return commentCount;
    }

    public BoardComment updateBoardComment(UpdateBoardCommentReqDTO updateBoardCommentReqDTO, Long commentId) {
        BoardComment boardComment = boardCommentPersistPort.getBoardCommentById(commentId);
        boardComment.update(updateBoardCommentReqDTO);

        return boardCommentPersistPort.saveBoardComment(boardComment);
    }


    public void deleteBoardCommentByBoardId(Long boardId) {
        List<BoardComment> boardComments = boardCommentPersistPort.getBoardCommentsByBoardId(boardId);
        boardComments.stream()
                .forEach(comment -> boardCommentPersistPort.deleteBoardComment(comment));
    }

    public Board getBoardByCommentId(Long commentId) {
        BoardComment boardComment = boardCommentPersistPort.getBoardCommentById(commentId);
        return boardComment.getBoard();
    }
}
