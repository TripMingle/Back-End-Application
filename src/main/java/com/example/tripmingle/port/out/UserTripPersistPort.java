package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.UserTrip;

import java.util.List;

public interface UserTripPersistPort {
    UserTrip saveUserTrip(UserTrip userTrip);

    UserTrip getUserTripById(Long userTripId);

    void deleteUserTripById(Long userTripId);

    List<UserTrip> getUserTripsByUserId(Long id);
}
