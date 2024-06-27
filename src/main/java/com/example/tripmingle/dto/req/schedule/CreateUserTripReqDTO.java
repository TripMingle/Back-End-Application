package com.example.tripmingle.dto.req.schedule;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateUserTripReqDTO {

    private String continent;
    private String countryName;
    private LocalDate startDate;
    private LocalDate endDate;

}
