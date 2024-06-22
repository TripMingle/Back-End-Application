package com.example.tripmingle.dto.etc;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class UpdateBoardDTO {
    private String continent;
    private String countryName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String language;
    private int maxCount;
    private String tripType;
    private String personalType;
    private String title;
    private String content;
}
