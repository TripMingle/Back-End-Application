package com.example.tripmingle.dto.req.board;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class GetAllBoardReqDTO {
    private String countryName;
    private String gender;
    private String language;
}
