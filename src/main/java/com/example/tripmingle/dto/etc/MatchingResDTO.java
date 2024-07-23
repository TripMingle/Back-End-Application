package com.example.tripmingle.dto.etc;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class MatchingResDTO {
	private String message;
	private List<Long> boardId;
	private String messageId;
}
