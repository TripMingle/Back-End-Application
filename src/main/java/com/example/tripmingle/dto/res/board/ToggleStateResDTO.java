package com.example.tripmingle.dto.res.board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ToggleStateResDTO {
    private Long boardId;
    private boolean state;
}
