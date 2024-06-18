package com.example.tripmingle.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostingComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "posting_id", nullable = false)
    private Posting posting;

    @ManyToOne
    @JoinColumn(name = "posting_comment_id")
    private PostingComment postingComment;

    private String comment;

    @Builder
    public PostingComment(User user, Posting posting, PostingComment postingComment, String comment) {
        this.user = user;
        this.posting = posting;
        this.postingComment = postingComment;
        this.comment = comment;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }
}
