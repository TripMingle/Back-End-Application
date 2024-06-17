package com.example.tripmingle.entity;

import com.example.tripmingle.common.entity.BaseEntity;
import com.example.tripmingle.dto.req.UpdateBoardCommentReqDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "is_deleted = false")
public class BoardComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_comment_id", nullable = true)
    private BoardComment parentBoardComment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    private String content;

    public boolean isParentBoardCommentNull(){
        return this.parentBoardComment==null?true:false;
    }

    public void update(UpdateBoardCommentReqDTO updateBoardCommentReqDTO){
        this.content = updateBoardCommentReqDTO.getContent();
    }

}
