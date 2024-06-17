package com.example.tripmingle.repository;

import com.example.tripmingle.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query("SELECT c FROM Country c WHERE LOWER(c.continent) LIKE LOWER(CONCAT('%', :continent, '%'))" +
            "OR LOWER(c.continentEnglish) LIKE LOWER(CONCAT('%', :continent, '%'))")
    List<Country> findCountriesByContinent(@Param("continent") String continent);

    @Query("SELECT c FROM Country c WHERE LOWER(c.country) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(c.countryEnglish) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Country> getCountriesByKeyword(@Param("keyword") String keyword);
}
