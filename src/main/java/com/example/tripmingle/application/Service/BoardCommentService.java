package com.example.tripmingle.application.Service;

import com.example.tripmingle.dto.res.BoardCommentResDTO;
import com.example.tripmingle.entity.BoardComment;
import com.example.tripmingle.port.out.BoardCommentPersistPort;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class BoardCommentService {
    private final BoardCommentPersistPort boardCommentPersistPort;

    public List<BoardCommentResDTO> getStructureBoardComment(Long boardId) {
        List<BoardComment> boardComments = boardCommentPersistPort.getBoardCommentsByBoardId(boardId);

        Map<Integer, List<Integer>> commentMap = new HashMap<>();
        List<Integer> parentList = new ArrayList<>();

        for(int i = 0 ; i < boardComments.size() ; i++){
            Long commentId = boardComments.get(i).getId();

            if(boardComments.get(i).getParentBoardComment() == null){
                commentMap.put(i, new ArrayList<>());
                parentList.add(i);
            }
            else{
                Long parentId = boardComments.get(i).getParentBoardComment().getId();
                commentMap.get(parentId).add(i);
            }
        }

        List<BoardCommentResDTO> boardCommentResDTOS = new ArrayList<>();

        for(int i = 0 ; i < parentList.size() ; i++){
            boardCommentResDTOS.add(getBoardCommentInfo(boardComments.get(parentList.get(i))));
            for(int j=0;j<commentMap.get(parentList.get(i)).size();j++){
                boardCommentResDTOS.add(getBoardCommentInfo(boardComments.get(commentMap.get(parentList.get(i)).get(j))));
            }
        }

        return boardCommentResDTOS;
    }

    private BoardCommentResDTO getBoardCommentInfo(BoardComment boardComment){
        Long parentId = (boardComment.getParentBoardComment() == null ? -1 : boardComment.getParentBoardComment().getId());

        return BoardCommentResDTO.builder()
                .boardId(boardComment.getId())
                .parentCommentId(parentId)
                .content(boardComment.getContent())
                .registeredDate(boardComment.getCreatedAt())
                .userId(boardComment.getUser().getId())
                .userNickname(boardComment.getUser().getNickName())
                .build();
    }

    public void createBoardComment() {
        boardCommentPersistPort.saveBoardComment();
    }

    public void deleteBoardComment() {
        boardCommentPersistPort.deleteBoardCommentById();
    }

    public void getBoardCommentById() {
        boardCommentPersistPort.getBoardCommentsById();
    }

    public void updateBoardComment() {
    }
}
