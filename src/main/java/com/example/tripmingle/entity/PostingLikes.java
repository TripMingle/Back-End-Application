package com.example.tripmingle.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostingLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "posting_id", nullable = false)
    private Posting posting;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private boolean toggleState;

    @Builder
    public PostingLikes(Posting posting, User user) {
        this.posting = posting;
        this.user = user;
        this.toggleState = true;
    }

    public void updatePostingLikeToggleState() {
        this.toggleState = !this.toggleState;
    }
}
