package com.example.tripmingle.common.utils;

import com.example.tripmingle.adapter.out.UserPersistAdapter;
import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.InvalidUserAccessException;
import com.example.tripmingle.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtils {

    private final UserPersistAdapter userPersistAdapter;

    public boolean validateMasterUser(Long validatingUserId) {
        User user = userPersistAdapter.findCurrentUserByEmail();
        if (validatingUserId.equals(user.getId())) {
            return true;
        } else {
            throw new InvalidUserAccessException("Invalid User Access.", ErrorCode.INVALID_USER_ACCESS);
        }
    }

}
