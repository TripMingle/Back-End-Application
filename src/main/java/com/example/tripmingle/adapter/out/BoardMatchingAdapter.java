package com.example.tripmingle.adapter.out;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.tripmingle.client.MatchingClient;
import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.MatchingServerException;
import com.example.tripmingle.dto.etc.MatchingBoardPublishDTO;
import com.example.tripmingle.dto.etc.MatchingResDTO;
import com.example.tripmingle.port.out.BoardMatchingPort;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BoardMatchingAdapter implements BoardMatchingPort {
	private final MatchingClient matchingClient;

	@Override
	public List<Long> matchingBoard(MatchingBoardPublishDTO matchingBoardPublishDTO) {
		ResponseEntity<MatchingResDTO> result;
		try {
			result = matchingClient.matchingBoard(matchingBoardPublishDTO);
		} catch (FeignException e) {
			throw new MatchingServerException("매칭서버 오류", ErrorCode.MATCHING_SERVER_EXCEPTION);
		}
		return result.getBody().getBoardId();
	}

}
