package com.example.tripmingle.adapter.out;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.CountryNotFoundException;
import com.example.tripmingle.entity.Country;
import com.example.tripmingle.port.out.CountryPersistPort;
import com.example.tripmingle.repository.CountryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CountryPersistAdapter implements CountryPersistPort {
	private final CountryRepository countryRepository;

	@Override
	public List<Country> getCountriesByContinent(String continent) {
		return countryRepository.findCountriesByContinent(continent);
	}

	@Override
	public List<Country> getCountriesByKeyword(String keyword) {
		return countryRepository.getCountriesByKeyword(keyword);
	}

	@Override
	public Country getCountryByCountryName(String countryName) {
		return countryRepository.findCountryByCountryName(countryName)
			.orElseThrow(() -> new CountryNotFoundException("wrong country name", ErrorCode.COUNTRY_NOT_FOUND));
	}
}
