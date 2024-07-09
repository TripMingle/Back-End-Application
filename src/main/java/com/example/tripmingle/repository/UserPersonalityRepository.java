package com.example.tripmingle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tripmingle.entity.UserPersonality;

@Repository
public interface UserPersonalityRepository extends JpaRepository<UserPersonality, Long> {
	boolean existsByUserId(Long id);

	Optional<UserPersonality> findUserPersonalityById(Long userPersonalityId);

	Optional<UserPersonality> findUserPersonalityByUserId(Long userId);

	List<UserPersonality> findUserPersonalitiesByUser_IdIn(List<Long> userIds);
}
