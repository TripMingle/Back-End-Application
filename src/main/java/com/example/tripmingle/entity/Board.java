package com.example.tripmingle.entity;

import com.example.tripmingle.common.entity.BaseEntity;
import com.example.tripmingle.dto.etc.UpdateBoardDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "is_deleted = false")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;
    private String content;

    private String continent;
    private String countryName;

    //동행자 특성 list
    private String traits;

    //여행 타입 list
    private String types;

    //인원수
    private int currentCount;
    private int maxCount;

    private LocalDate startDate;
    private LocalDate endDate;

    private String language;

    private int commentCount;


    public void updateCommentCount(int diff) {
        this.commentCount += diff;
    }

    public void update(UpdateBoardDTO updateBoardDTO) {
        if (updateBoardDTO.getTitle() != null) {
            this.title = updateBoardDTO.getTitle();
        }
        if (updateBoardDTO.getContent() != null) {
            this.content = updateBoardDTO.getContent();
        }
        if (updateBoardDTO.getContinent() != null) {
            this.continent = updateBoardDTO.getContinent();
        }
        if (updateBoardDTO.getCountryName() != null) {
            this.countryName = updateBoardDTO.getCountryName();
        }
        if (updateBoardDTO.getTraits() != null) {
            this.traits = updateBoardDTO.getTraits();
        }
        if (updateBoardDTO.getTypes() != null) {
            this.types = updateBoardDTO.getTypes();
        }
        if (updateBoardDTO.getMaxCount() > 0) {
            this.maxCount = updateBoardDTO.getMaxCount();
        }
        if (updateBoardDTO.getStartDate() != null) {
            this.startDate = updateBoardDTO.getStartDate();
        }
        if (updateBoardDTO.getEndDate() != null) {
            this.endDate = updateBoardDTO.getEndDate();
        }
        if (updateBoardDTO.getLanguage() != null) {
            this.language = updateBoardDTO.getLanguage();
        }
    }
}
