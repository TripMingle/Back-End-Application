package com.example.tripmingle.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.tripmingle.entity.Country;
import com.example.tripmingle.port.out.CountryPersistPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CountryService {
	private final CountryPersistPort countryPersistPort;

	public List<Country> getCountriesByContinent(String continent) {
		return countryPersistPort.getCountriesByContinent(continent);
	}

	public List<Country> getCountriesByKeyword(String keyword) {
		return countryPersistPort.getCountriesByKeyword(keyword);
	}

	public Country getCountryByCountryName(String countryName) {
		return countryPersistPort.getCountryByCountryName(countryName);
	}
}
