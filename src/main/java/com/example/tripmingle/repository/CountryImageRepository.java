package com.example.tripmingle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.tripmingle.entity.CountryImage;

@Repository
public interface CountryImageRepository extends JpaRepository<CountryImage, Long> {
	Optional<CountryImage> findCountryImageByImageUrl(String imageUrl);

	@Query("SELECT ci FROM CountryImage ci WHERE ci.country.id = :countryId AND ci.isPrimary = true")
	Optional<CountryImage> getPrimaryImageByCountryId(@Param("countryId") Long countryId);

	@Query("SELECT ci FROM CountryImage ci " +
		"WHERE (LOWER(ci.country.country) = LOWER(:countryName) " +
		"OR LOWER(ci.country.countryEnglish) = LOWER(:countryName)) " +
		"AND ci.isPrimary = false")
	List<CountryImage> findCountryImageByCountryName(@Param("countryName") String countryName);
}
