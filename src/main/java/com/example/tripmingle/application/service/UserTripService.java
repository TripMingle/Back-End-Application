package com.example.tripmingle.application.service;

import com.example.tripmingle.dto.req.schedule.CreateUserTripReqDTO;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.entity.UserTrip;
import com.example.tripmingle.port.out.UserTripPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTripService {
    private final UserTripPersistPort userTripPersistPort;

    public UserTrip createUserTrip(User currentUser, CreateUserTripReqDTO createUserTripReqDTO) {
        UserTrip userTrip = UserTrip.builder()
                .user(currentUser)
                .continent(createUserTripReqDTO.getContinent())
                .countryName(createUserTripReqDTO.getCountryName())
                .startDate(createUserTripReqDTO.getStartDate())
                .endDate(createUserTripReqDTO.getEndDate())
                .build();
        return userTripPersistPort.saveUserTrip(userTrip);
    }

    public UserTrip getUserTripById(Long userTripId) {
        return userTripPersistPort.getUserTripById(userTripId);
    }

    public void deleteUserTripById(Long userTripId) {
        userTripPersistPort.deleteUserTripById(userTripId);
    }

    public List<UserTrip> getUserTripsByUser(User currentUser) {
        return userTripPersistPort.getUserTripsByUserId(currentUser.getId());
    }
}
