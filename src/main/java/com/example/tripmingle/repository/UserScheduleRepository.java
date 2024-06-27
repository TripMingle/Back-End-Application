package com.example.tripmingle.repository;

import com.example.tripmingle.entity.UserSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserScheduleRepository extends JpaRepository<UserSchedule,Long> {
    List<UserSchedule> findUserSchedulesByUserTripId(Long userTripId);
}
