package com.example.tripmingle.port.out;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.tripmingle.entity.Country;

@Component
public interface CountryPersistPort {

	List<Country> getCountriesByContinent(String continent);

	List<Country> getCountriesByKeyword(String keyword);

	Country getCountryByCountryName(String countryName);
}
