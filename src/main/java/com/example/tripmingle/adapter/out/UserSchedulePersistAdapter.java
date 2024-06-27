package com.example.tripmingle.adapter.out;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.UserScheduleNotFoundException;
import com.example.tripmingle.dto.req.schedule.UpdateUserScheduleReqDTO;
import com.example.tripmingle.entity.UserSchedule;
import com.example.tripmingle.port.out.UserSchedulePersistPort;
import com.example.tripmingle.repository.UserScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserSchedulePersistAdapter implements UserSchedulePersistPort {
    private final UserScheduleRepository userScheduleRepository;
    @Override
    public UserSchedule saveUserSchedule(UserSchedule userSchedule) {
        return userScheduleRepository.save(userSchedule);
    }

    @Override
    public void updateUserSchedule(UpdateUserScheduleReqDTO userScheduleReqDTO) {
        UserSchedule userSchedule = userScheduleRepository.findById(userScheduleReqDTO.getUserScheduleId())
                .orElseThrow(()-> new UserScheduleNotFoundException("user schedule not found", ErrorCode.USER_SCHEDULE_NOT_FOUND));

        userSchedule.update(userScheduleReqDTO);
    }

    @Override
    public void deleteUserSchedule(Long userScheduleId) {
        UserSchedule userSchedule = userScheduleRepository.findById(userScheduleId)
                .orElseThrow(()-> new UserScheduleNotFoundException("user schedule not found", ErrorCode.USER_SCHEDULE_NOT_FOUND));
        userSchedule.delete();
    }

    @Override
    public List<UserSchedule> getUserScheduleByUserTripId(Long userTripId) {
        return userScheduleRepository.findUserSchedulesByUserTripId(userTripId);
    }

    @Override
    public void deleteUserScheduleByUserTripId(Long userTripId) {
        userScheduleRepository.findUserSchedulesByUserTripId(userTripId).forEach(userSchedule -> userSchedule.delete());
    }
}
