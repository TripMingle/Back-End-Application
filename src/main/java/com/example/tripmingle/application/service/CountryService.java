package com.example.tripmingle.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.tripmingle.dto.res.country.GetCountriesResDTO;
import com.example.tripmingle.entity.Country;
import com.example.tripmingle.port.out.CacheManagerPort;
import com.example.tripmingle.port.out.CountryPersistPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CountryService {
	private final CountryPersistPort countryPersistPort;
	private final CacheManagerPort cacheManagerPort;

	public List<Country> getCountriesByContinent(String continent) {
		return countryPersistPort.getCountriesByContinent(continent);
	}

	public List<Country> getCountriesByKeyword(String keyword) {
		return countryPersistPort.getCountriesByKeyword(keyword);
	}

	public Country getCountryByCountryName(String countryName) {
		return countryPersistPort.getCountryByCountryName(countryName);
	}

	public List<GetCountriesResDTO> getCountriesAtCache(String continent) {
		return cacheManagerPort.getCountriesAtCache(continent);
	}

	public void setCountryAtCache(String continent, List<GetCountriesResDTO> getCountriesResDTOS) {
		cacheManagerPort.setCountriesAtCache(continent, getCountriesResDTOS);
	}
}
