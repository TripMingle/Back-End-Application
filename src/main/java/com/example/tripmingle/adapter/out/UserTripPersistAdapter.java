package com.example.tripmingle.adapter.out;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.UserTripNotFoundException;
import com.example.tripmingle.entity.UserTrip;
import com.example.tripmingle.port.out.UserTripPersistPort;
import com.example.tripmingle.repository.UserTripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserTripPersistAdapter implements UserTripPersistPort {
    private final UserTripRepository userTripRepository;
    @Override
    public UserTrip saveUserTrip(UserTrip userTrip) {
        return userTripRepository.save(userTrip);
    }

    @Override
    public UserTrip getUserTripById(Long userTripId) {
        return userTripRepository.findById(userTripId)
                .orElseThrow(()-> new UserTripNotFoundException("usertrip not found", ErrorCode.USER_TRIP_NOT_FOUND));
    }

    @Override
    public void deleteUserTripById(Long userTripId) {
        UserTrip userTrip = userTripRepository.findById(userTripId)
                .orElseThrow(()-> new UserTripNotFoundException("usertrip not found", ErrorCode.USER_TRIP_NOT_FOUND));
        userTrip.delete();
    }

    @Override
    public List<UserTrip> getUserTripsByUserId(Long userId) {
        return userTripRepository.findUserTripsByUserId(userId);
    }
}
