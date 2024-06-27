package com.example.tripmingle.application.service;

import com.example.tripmingle.common.utils.UserUtils;
import com.example.tripmingle.dto.req.schedule.CreateUserScheduleReqDTO;
import com.example.tripmingle.dto.req.schedule.DeleteUserScheduleReqDTO;
import com.example.tripmingle.dto.req.schedule.UpdateUserScheduleReqDTO;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.entity.UserSchedule;
import com.example.tripmingle.entity.UserTrip;
import com.example.tripmingle.port.out.UserSchedulePersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserScheduleService {
    private final UserUtils userUtils;
    private final UserSchedulePersistPort userSchedulePersistPort;
    public List<UserSchedule> createUserSchedule(User currentUser, UserTrip userTrip, List<CreateUserScheduleReqDTO> createUserScheduleReqDTOS) {
        userUtils.validateMasterUser(userTrip.getUser().getId(),currentUser.getId());

        return createUserScheduleReqDTOS.stream()
                .map(DTO->{
                    UserSchedule userSchedule = UserSchedule.builder()
                            .userTrip(userTrip)
                            .date(DTO.getDate())
                            .placeName(DTO.getPlaceName())
                            .number(DTO.getNumber())
                            .pointX(DTO.getPointX())
                            .pointY(DTO.getPointY())
                            .googlePlaceId(DTO.getGooglePlaceId())
                            .build();
                    return userSchedulePersistPort.saveUserSchedule(userSchedule);
                }).collect(Collectors.toList());
    }

    public void updateUserSchedule(User currentUser, UserTrip userTrip, List<UpdateUserScheduleReqDTO> updateUserScheduleReqDTOS) {
        userUtils.validateMasterUser(currentUser.getId(), userTrip.getUser().getId());
        updateUserScheduleReqDTOS.forEach( DTO -> userSchedulePersistPort.updateUserSchedule(DTO));
    }

    public void deleteUserSchedule(User currentUser, UserTrip userTrip, List<DeleteUserScheduleReqDTO> deleteUserScheduleReqDTOS) {
        userUtils.validateMasterUser(currentUser.getId(), userTrip.getUser().getId());
        deleteUserScheduleReqDTOS.forEach( DTO -> userSchedulePersistPort.deleteUserSchedule(DTO.getUserScheduleId()));
    }

    public List<UserSchedule> getUserScheduleByUserTripId(Long userTripId) {
        return userSchedulePersistPort.getUserScheduleByUserTripId(userTripId);
    }

    public void deleteUserScheduleByUserTripId(Long userTripId) {
        userSchedulePersistPort.deleteUserScheduleByUserTripId(userTripId);
    }
}
