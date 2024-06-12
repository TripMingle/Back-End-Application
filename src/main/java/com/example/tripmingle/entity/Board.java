package com.example.tripmingle.entity;

import com.example.tripmingle.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id", nullable = false)
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

}
