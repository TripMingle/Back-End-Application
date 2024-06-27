package com.example.tripmingle.repository;

import com.example.tripmingle.entity.UserTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTripRepository extends JpaRepository<UserTrip,Long> {
    List<UserTrip> findUserTripsByUserId(Long userId);
}
