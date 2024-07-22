package com.example.tripmingle.port.out;

import java.util.List;

import com.example.tripmingle.dto.req.schedule.UpdateUserScheduleReqDTO;
import com.example.tripmingle.entity.UserSchedule;

public interface UserSchedulePersistPort {
	UserSchedule saveUserSchedule(UserSchedule userSchedule);

	void updateUserSchedule(UpdateUserScheduleReqDTO updateUserScheduleReqDTO);

	void deleteUserSchedule(Long userScheduleId);

	List<UserSchedule> getUserScheduleByUserTripId(Long userTripId);

	void deleteUserScheduleByUserTripId(Long userTripId);

	void flushUserSchedule();

	UserSchedule getUserSchedulesById(Long l);
}
