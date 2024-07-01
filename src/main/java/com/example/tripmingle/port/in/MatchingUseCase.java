package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.matching.PostUserPersonalityReqDTO;
import com.example.tripmingle.dto.res.matching.MatchingUserResDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MatchingUseCase {
    Page<MatchingUserResDTO> getMyMatchingUsers(Pageable pageable);

    void postUserPersonality(PostUserPersonalityReqDTO postUserPersonalityReqDTO);
}
