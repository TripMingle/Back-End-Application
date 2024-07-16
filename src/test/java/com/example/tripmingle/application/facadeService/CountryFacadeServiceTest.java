package com.example.tripmingle.application.facadeService;

import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class CountryFacadeServiceTest {

	@Autowired
	private CountryFacadeService countryFacadeService;

	@Test
	@DirtiesContext
	void getCountriesWithCacheTest() {
		int readCount = 100000;
		String[] countryList = {"europe", "south america", "north america", "asia", "africa", "oceania"};
		Instant start = Instant.now();
		for (int i = 0; i < readCount; i++) {
			countryFacadeService.getCountries(countryList[i % 6]);
		}
		Instant end = Instant.now();
		Duration duration = Duration.between(start, end);
		System.out.println("With Cache Execution time: " + duration.toMillis() + " milliseconds");

	}

	@Test
	@DirtiesContext
	void getCountriesWithoutCacheTest() {
		int readCount = 100000;
		String[] countryList = {"europe", "south america", "north america", "asia", "africa", "oceania"};
		Instant start = Instant.now();
		for (int i = 0; i < readCount; i++) {
			countryFacadeService.getCountriesWithoutCache(countryList[i % 6]);
		}
		Instant end = Instant.now();
		Duration duration = Duration.between(start, end);
		System.out.println("Without Cache Execution time: " + duration.toMillis() + " milliseconds");

	}
}