package com.example.tripmingle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tripmingle.entity.UserSchedule;

@Repository
public interface UserScheduleRepository extends JpaRepository<UserSchedule, Long> {
	List<UserSchedule> findUserSchedulesByUserTripId(Long userTripId);

	UserSchedule findByNumber(int i);
}
