package com.example.tripmingle.entity;

import com.example.tripmingle.common.entity.BaseEntity;
import com.example.tripmingle.dto.req.posting.PatchPostingReqDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted = false")
public class Posting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PostingType postingType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String country;

    private int commentCount;

    public void updatePosting(PatchPostingReqDTO patchPostingReqDTO) {
        if (patchPostingReqDTO.getTitle() != null && !patchPostingReqDTO.getTitle().equals("")) {
            this.title = patchPostingReqDTO.getTitle();
        }
        if (patchPostingReqDTO.getContent() != null && !patchPostingReqDTO.getContent().equals("")) {
            this.content = patchPostingReqDTO.getContent();
        }
        if (patchPostingReqDTO.getPostingType() != null && !patchPostingReqDTO.getPostingType().equals("")) {
            this.postingType = patchPostingReqDTO.getPostingType();
        }
        if (patchPostingReqDTO.getCountry() != null && !patchPostingReqDTO.getCountry().equals("")) {
            this.country = patchPostingReqDTO.getCountry();
        }
    }

    public void deletePosting() {
        delete();
    }

    public void increasePostingCommentCount() {
        this.commentCount += 1;
    }

    public void decreasePostingCommentCount() {
        this.commentCount -= 1;
    }
}
