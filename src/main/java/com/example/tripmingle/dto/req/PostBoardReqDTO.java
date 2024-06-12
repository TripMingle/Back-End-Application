package com.example.tripmingle.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PostBoardReqDTO {
    private String countryName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String language;
    private List<String> types;
}
