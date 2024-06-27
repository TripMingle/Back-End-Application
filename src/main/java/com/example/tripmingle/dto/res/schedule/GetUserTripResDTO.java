package com.example.tripmingle.dto.res.schedule;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class GetUserTripResDTO {
    private String continent;
    private String countryName;
    private LocalDate startDate;
    private LocalDate endDate;
}
