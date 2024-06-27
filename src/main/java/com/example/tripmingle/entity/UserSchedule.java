package com.example.tripmingle.entity;

import com.example.tripmingle.common.entity.BaseEntity;
import com.example.tripmingle.dto.req.schedule.UpdateUserScheduleReqDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_deleted = false")
public class UserSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_trip_id", nullable = false)
    private UserTrip userTrip;

    private LocalDate date;
    private String placeName;
    private int number;
    private double pointX;
    private double pointY;
    private String googlePlaceId;

    public void update(UpdateUserScheduleReqDTO userScheduleReqDTO){
        this.date = userScheduleReqDTO.getDate();
        this.placeName = userScheduleReqDTO.getPlaceName();
        this.number = userScheduleReqDTO.getNumber();
        this.pointX = userScheduleReqDTO.getPointX();
        this.pointY = userScheduleReqDTO.getPointY();
        this.googlePlaceId = userScheduleReqDTO.getGooglePlaceId();
    }

}
