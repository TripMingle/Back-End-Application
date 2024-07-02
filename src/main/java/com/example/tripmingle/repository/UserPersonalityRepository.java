package com.example.tripmingle.repository;

import com.example.tripmingle.entity.UserPersonality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPersonalityRepository extends JpaRepository<UserPersonality, Long> {
    boolean existsByUserId(Long id);

    Optional<UserPersonality> findUserPersonalityById(Long userPersonalityId);
}
