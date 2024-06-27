package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.schedule.CreateUserScheduleReqDTO;
import com.example.tripmingle.dto.req.schedule.CreateUserTripReqDTO;
import com.example.tripmingle.dto.req.schedule.DeleteUserScheduleReqDTO;
import com.example.tripmingle.dto.req.schedule.UpdateUserScheduleReqDTO;
import com.example.tripmingle.dto.res.schedule.CreateUserTripResDTO;
import com.example.tripmingle.dto.res.schedule.GetUserScheduleResDTO;
import com.example.tripmingle.dto.res.schedule.GetUserTripResDTO;
import com.example.tripmingle.dto.res.schedule.UserScheduleResDTO;

import java.util.List;

public interface UserScheduleUseCase {
    CreateUserTripResDTO createUserTrip(CreateUserTripReqDTO createUserTripReqDTO);

    List<UserScheduleResDTO> createUserSchedule(Long userTripId, List<CreateUserScheduleReqDTO> createUserScheduleReqDTOS);

    void deleteUserTrip(Long userTripId);

    void modifyUserSchedule(Long userTripId, List<UpdateUserScheduleReqDTO> updateUserScheduleReqDTOS, List<DeleteUserScheduleReqDTO> deleteUserScheduleReqDTOS);

    List<GetUserTripResDTO> getUserTrip();

    GetUserScheduleResDTO getUserSchedule(Long userTripId);
}
