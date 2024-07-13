package com.example.tripmingle.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.tripmingle.dto.req.matching.MatchingBoardReqDTO;
import com.example.tripmingle.dto.req.matching.PostUserPersonalityReqDTO;
import com.example.tripmingle.dto.res.matching.AddUserResDTO;
import com.example.tripmingle.dto.res.matching.MatchingBoardResDTO;
import com.example.tripmingle.dto.res.matching.MatchingUserResDTO;
import com.example.tripmingle.entity.UserPersonality;

public interface MatchingUseCase {
	Page<MatchingUserResDTO> getMyMatchingUsers(Pageable pageable);

	AddUserResDTO addUserPersonality(UserPersonality userPersonality);

	UserPersonality saveUserPersonality(PostUserPersonalityReqDTO postUserPersonalityReqDTO);

	void deleteUserPersonality();

	MatchingBoardResDTO matchingBoard(MatchingBoardReqDTO matchingBoardReqDTO);
}
