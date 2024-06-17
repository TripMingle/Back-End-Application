package com.example.tripmingle.application.Service;

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

@Service
@RequiredArgsConstructor
public class BoardCommentService {
    private final BoardCommentPersistPort boardCommentPersistPort;
    private final UserPersistPort userPersistPort;

    //추후 부모댓글이 없어질경우 등을 고려하여 리팩터링 필요
    public List<ParentBoardCommentResDTO> getStructureBoardComment(Long boardId) {
        List<BoardComment> boardComments = boardCommentPersistPort.getBoardCommentsByBoardId(boardId);
        Optional<User> currentUser = userPersistPort.getCurrentUser();
        Map<Long, List<BoardComment>> commentMap = new HashMap<>();
        List<BoardComment> parentList = new ArrayList<>();


        for(BoardComment boardComment : boardComments){
            Long commentId = boardComment.getId();

            if(boardComment.isParentBoardCommentNull()){
                commentMap.put(commentId, new ArrayList<>());
                parentList.add(boardComment);
            }
            else{
                Long parentId = boardComment.getParentBoardComment().getId();
                commentMap.get(parentId).add(boardComment);
            }
        }

        List<ParentBoardCommentResDTO> boardCommentResDTOS = new ArrayList<>();

        for(BoardComment parentBoardComment : parentList){
            Long parentId = parentBoardComment.getId();
            ParentBoardCommentResDTO parentBoardCommentResDTO = getParentBoardCommentInfo(parentBoardComment, currentUser.get());
            List<ChildBoardCommentDTO> childBoardCommentDTOS = new ArrayList<>();

            for (BoardComment childBoardComment : commentMap.get(parentId)) {
                childBoardCommentDTOS.add(getChildBoardCommentInfo(childBoardComment, parentId, currentUser.get()));
            }

            parentBoardCommentResDTO.setChildBoards(childBoardCommentDTOS);
            boardCommentResDTOS.add(parentBoardCommentResDTO);
        }

        return boardCommentResDTOS;
    }

    private ParentBoardCommentResDTO getParentBoardCommentInfo(BoardComment boardComment, User currentUser){

        return ParentBoardCommentResDTO.builder()
                .boardId(boardComment.getBoard().getId())
                .boardCommentId(boardComment.getId())
                .content(boardComment.getContent())
                .registeredDate(boardComment.getCreatedAt())
                .userId(boardComment.getUser().getId())
                .userNickname(boardComment.getUser().getNickName())
                .isMine(currentUser.getId() == boardComment.getUser().getId())
                .build();
    }

    private ChildBoardCommentDTO getChildBoardCommentInfo(BoardComment boardComment, Long parentId, User currentUser){

        return ChildBoardCommentDTO.builder()
                .boardId(boardComment.getBoard().getId())
                .boardCommentId(boardComment.getId())
                .parentId(parentId)
                .content(boardComment.getContent())
                .registeredDate(boardComment.getCreatedAt())
                .userId(boardComment.getUser().getId())
                .userNickname(boardComment.getUser().getNickName())
                .isMine(currentUser.getId() == boardComment.getUser().getId())
                .build();
    }

    public BoardComment createBoardComment(CreateBoardCommentReqDTO createBoardCommentReqDTO, Board board) {
        BoardComment parentBoardComment;
        Optional<User> user = userPersistPort.getCurrentUser();
        if(isParent(createBoardCommentReqDTO.getParentBoardCommentId())){
            parentBoardComment = null;
        }
        else{
            parentBoardComment = boardCommentPersistPort
                    .getBoardCommentById(createBoardCommentReqDTO.getParentBoardCommentId());
        }

        return boardCommentPersistPort.saveBoardComment(BoardComment.builder()
                .parentBoardComment(parentBoardComment)
                .user(user.get())
                .board(board)
                .content(createBoardCommentReqDTO.getContent())
                .build());
    }

    private boolean isParent(Long id){
        if(id==-1)return true;
        else return false;
    }

    public void deleteBoardComment(Long commentId) {
        BoardComment boardComment = boardCommentPersistPort.getBoardCommentById(commentId);
        if(boardComment.isParentBoardCommentNull()){
            List<BoardComment> childBoardComments = boardCommentPersistPort.getBoardCommentByParentBoardId(boardComment.getId());

            childBoardComments.stream()
                    .forEach(childComment -> boardCommentPersistPort.deleteBoardCommentById(childComment.getId()));

        }

        boardCommentPersistPort.deleteBoardCommentById(commentId);
    }

    public BoardComment updateBoardComment(UpdateBoardCommentReqDTO updateBoardCommentReqDTO, Long commentId) {
        BoardComment boardComment = boardCommentPersistPort.getBoardCommentById(commentId);
        boardComment.update(updateBoardCommentReqDTO);

        return boardCommentPersistPort.saveBoardComment(boardComment);
    }


    public void deleteBoardCommentByBoardId(Long boardId) {
        List<BoardComment> boardComments = boardCommentPersistPort.getBoardCommentsByBoardId(boardId);
        boardComments.stream()
                .forEach(comments -> boardCommentPersistPort.deleteBoardCommentById(comments.getId()));
    }
}
