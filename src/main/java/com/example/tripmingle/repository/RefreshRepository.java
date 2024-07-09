package com.example.tripmingle.repository;

import com.example.tripmingle.entity.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshRepository extends CrudRepository<Refresh, String> {
    Boolean existsByRefreshToken(String refresh);

    void deleteByEmail(String email);

    Optional<Refresh> findByEmail(String email);

}
