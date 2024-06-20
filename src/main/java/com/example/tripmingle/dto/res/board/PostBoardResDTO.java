package com.example.tripmingle.dto.res.board;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostBoardResDTO {
    private Long boardId;

}
