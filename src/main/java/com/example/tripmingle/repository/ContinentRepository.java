package com.example.tripmingle.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.tripmingle.entity.Continent;

@Repository
public interface ContinentRepository extends JpaRepository<Continent, Long> {
	@Query("SELECT c FROM Continent c WHERE LOWER(c.continentName) = LOWER(:continent) OR LOWER(c.continentEnglishName) = LOWER(:continent)")
	Optional<Continent> findContinentByContinentName(@Param("continent") String continent);
}
