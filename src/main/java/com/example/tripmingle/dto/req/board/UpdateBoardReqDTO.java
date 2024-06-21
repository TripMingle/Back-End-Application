package com.example.tripmingle.dto.req.board;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UpdateBoardReqDTO {
    private String continent;
    private String countryName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String language;
    private int maxCount;
    private List<String> tripType;
    private List<String> personalType;
    private String title;
    private String content;
}
