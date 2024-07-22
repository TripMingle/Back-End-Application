package com.example.tripmingle.port.out;

import java.util.List;

import com.example.tripmingle.dto.etc.MatchingBoardPublishDTO;

public interface MatchingPort {
	List<Long> matchingBoard(MatchingBoardPublishDTO build);
}
