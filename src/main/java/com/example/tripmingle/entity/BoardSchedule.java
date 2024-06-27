package com.example.tripmingle.entity;

import com.example.tripmingle.common.entity.BaseEntity;
import com.example.tripmingle.dto.req.schedule.UpdateBoardScheduleReqDTO;
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
public class BoardSchedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="board_id", nullable = false)
    private Board board;

    private LocalDate date;

    private String placeName;

    private int number;

    private double pointX;
    private double pointY;

    private String googlePlaceId;


    public void update(UpdateBoardScheduleReqDTO updateBoardScheduleReqDTO){
        this.date = updateBoardScheduleReqDTO.getDate();
        this.placeName = updateBoardScheduleReqDTO.getPlaceName();
        this.number = updateBoardScheduleReqDTO.getNumber();
        this.pointX = updateBoardScheduleReqDTO.getPointX();
        this.pointY = updateBoardScheduleReqDTO.getPointY();
        this.googlePlaceId = updateBoardScheduleReqDTO.getGooglePlaceId();
    }
}
