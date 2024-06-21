package com.example.tripmingle.application.service;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.InvalidUserAccessException;
import com.example.tripmingle.dto.req.auth.ValidateDuplicationReqDTO;
import com.example.tripmingle.dto.res.auth.ValidateDuplicationResDTO;
import com.example.tripmingle.entity.User;
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

    public boolean validateMasterUser(Long validatingUserId) {
        User user = userPersistPort.findCurrentUserByEmail();
        if (validatingUserId.equals(user.getId())) {
            return true;
        } else {
            throw new InvalidUserAccessException("Invalid User Access.", ErrorCode.INVALID_USER_ACCESS);
        }
    }
}
