package com.example.tripmingle.port.in;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.tripmingle.dto.req.matching.MatchingBoardReqDTO;
import com.example.tripmingle.dto.req.matching.PostUserPersonalityReqDTO;
import com.example.tripmingle.dto.res.matching.AddUserResDTO;
import com.example.tripmingle.dto.res.matching.MatchingBoardResDTO;
import com.example.tripmingle.dto.res.matching.MatchingUserResDTO;

public interface MatchingUseCase {
	Page<MatchingUserResDTO> getMyMatchingUsers(Pageable pageable);

	AddUserResDTO addUserPersonality(Long userPersonalityId);

	Page<MatchingUserResDTO> getMyMatchingUsersByElasticSearch(Pageable pageable);

	Page<MatchingUserResDTO> getMyMatchingUsersByHNSW(Pageable pageable);

	Long saveUserPersonality(PostUserPersonalityReqDTO postUserPersonalityReqDTO);

	List<MatchingBoardResDTO> matchingBoard(MatchingBoardReqDTO matchingBoardReqDTO);

	Long deleteAndSaveUserPersonality(PostUserPersonalityReqDTO postUserPersonalityReqDTO);

	void postUserPersonality(PostUserPersonalityReqDTO postUserPersonalityReqDTO);

	void changeUserPersonality(PostUserPersonalityReqDTO postUserPersonalityReqDTO);
}
