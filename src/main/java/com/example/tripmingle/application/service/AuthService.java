package com.example.tripmingle.application.service;

import com.example.tripmingle.dto.req.ValidateDuplicationReqDTO;
import com.example.tripmingle.dto.res.ValidateDuplicationResDTO;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserPersistPort userPersistPort;

    public ValidateDuplicationResDTO validateDuplication(ValidateDuplicationReqDTO validateDuplicationReqDTO) {
        boolean duplicationStatus = userPersistPort.existsByNickName(validateDuplicationReqDTO.getNickName());
        return ValidateDuplicationResDTO.builder()
                .duplicationStatus(duplicationStatus)
                .build();
    }
}
