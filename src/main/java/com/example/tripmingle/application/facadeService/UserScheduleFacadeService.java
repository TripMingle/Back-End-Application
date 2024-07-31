package com.example.tripmingle.application.facadeService;

import com.example.tripmingle.application.service.UserScheduleService;
import com.example.tripmingle.application.service.UserService;
import com.example.tripmingle.application.service.UserTripService;
import com.example.tripmingle.dto.req.schedule.CreateUserScheduleReqDTO;
import com.example.tripmingle.dto.req.schedule.CreateUserTripReqDTO;
import com.example.tripmingle.dto.req.schedule.DeleteUserScheduleReqDTO;
import com.example.tripmingle.dto.req.schedule.UpdateUserScheduleReqDTO;
import com.example.tripmingle.dto.res.schedule.*;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.entity.UserSchedule;
import com.example.tripmingle.entity.UserTrip;
import com.example.tripmingle.port.in.UserScheduleUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserScheduleFacadeService implements UserScheduleUseCase {
    private final UserTripService userTripService;
    private final UserScheduleService userScheduleService;
    private final UserService userService;

    @Override
    @Transactional(readOnly = false)
    public void deleteUserTrip(Long userTripId) {
        User currentUser = userService.getCurrentUser();
        userScheduleService.deleteUserScheduleByUserTripId(userTripId);
        userTripService.deleteUserTripById(userTripId);
    }

    @Override
    @Transactional(readOnly = false)
    public CreateUserTripResDTO createUserTrip(CreateUserTripReqDTO createUserTripReqDTO) {
        User currentUser = userService.getCurrentUser();
        UserTrip userTrip = userTripService.createUserTrip(currentUser, createUserTripReqDTO);
        return CreateUserTripResDTO.builder().userTripId(userTrip.getId()).build();
    }

    @Override
    @Transactional(readOnly = false)
    public List<UserScheduleResDTO> createUserSchedule(Long userTripId,
                                                       List<CreateUserScheduleReqDTO> createUserScheduleReqDTOS) {
        User currentUser = userService.getCurrentUser();
        UserTrip userTrip = userTripService.getUserTripById(userTripId);
        List<UserSchedule> userSchedules = userScheduleService.createUserSchedule(currentUser, userTrip,
                createUserScheduleReqDTOS);
        return userSchedules.stream().map(
                userSchedule -> UserScheduleResDTO.builder()
                        .userTripId(userTrip.getId())
                        .userScheduleId(userSchedule.getId())
                        .date(userSchedule.getDate())
                        .placeName(userSchedule.getPlaceName())
                        .number(userSchedule.getNumber())
                        .pointX(userSchedule.getPointX())
                        .pointY(userSchedule.getPointY())
                        .googlePlaceId(userSchedule.getGooglePlaceId())
                        .build()
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = false)
    public GetUserScheduleResDTO modifyUserSchedule(Long userTripId,
                                                    List<UpdateUserScheduleReqDTO> updateUserScheduleReqDTOS,
                                                    List<DeleteUserScheduleReqDTO> deleteUserScheduleReqDTOS) {
        User currentUser = userService.getCurrentUser();
        UserTrip userTrip = userTripService.getUserTripById(userTripId);
        userScheduleService.updateUserSchedule(currentUser, userTrip, updateUserScheduleReqDTOS);
        userScheduleService.deleteUserSchedule(currentUser, userTrip, deleteUserScheduleReqDTOS);
        List<UserSchedule> userSchedules = userScheduleService.getUserScheduleByUserTripId(userTrip.getId());

        List<UserScheduleDTO> userScheduleDTOList = userSchedules.stream()
                .map(userSchedule -> makeUserScheduleDTO(userTrip,userSchedule))
                .collect(Collectors.toList());

        return GetUserScheduleResDTO.builder()
                .userTripId(userTrip.getId())
                .userSchedules(userScheduleDTOList)
                .build();
    }

    @Override
    public List<GetUserTripResDTO> getUserTrip() {
        User currentUser = userService.getCurrentUser();
        List<UserTrip> userTrips = userTripService.getUserTripsByUser(currentUser);
        return userTrips.stream()
                .map(userTrip -> GetUserTripResDTO.builder()
                        .userTripId(userTrip.getId())
                        .continent(userTrip.getContinent())
                        .countryName(userTrip.getCountryName())
                        .startDate(userTrip.getStartDate())
                        .endDate(userTrip.getEndDate())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public GetUserScheduleResDTO getUserSchedule(Long userTripId) {
        User currentUser = userService.getCurrentUser();
        UserTrip userTrip = userTripService.getUserTripById(userTripId);
        List<UserSchedule> userSchedules = userScheduleService.getUserScheduleByUserTripId(userTrip.getId());
        List<UserScheduleDTO> userScheduleDTOList = userSchedules.stream()
                .map(userSchedule -> makeUserScheduleDTO(userTrip,userSchedule))
                .collect(Collectors.toList());

        return GetUserScheduleResDTO.builder()
                .userTripId(userTrip.getId())
                .userSchedules(userScheduleDTOList)
                .build();
    }

    private UserScheduleDTO makeUserScheduleDTO(UserTrip userTrip, UserSchedule userSchedule){

        return UserScheduleDTO.builder()
                .userTripId(userTrip.getId())
                .userScheduleId(userSchedule.getId())
                .date(userSchedule.getDate())
                .placeName(userSchedule.getPlaceName())
                .number(userSchedule.getNumber())
                .pointX(userSchedule.getPointX())
                .pointY(userSchedule.getPointY())
                .googlePlaceId(userSchedule.getGooglePlaceId())
                .build();
    }


}
