package com.example.tripmingle.common.utils;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.InvalidUserAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtils {
    public void validateMasterUser(Long validatingUserId, Long currentUserId) {
        if (!validatingUserId.equals(currentUserId)) {
            throw new InvalidUserAccessException("Invalid User Access.", ErrorCode.INVALID_USER_ACCESS);
        }
    }

}
