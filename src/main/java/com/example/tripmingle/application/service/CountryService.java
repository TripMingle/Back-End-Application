package com.example.tripmingle.application.service;

import com.example.tripmingle.dto.res.count.GetCountriesResDTO;
import com.example.tripmingle.entity.Country;
import com.example.tripmingle.port.in.CountryUseCase;
import com.example.tripmingle.port.out.CountryPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService implements CountryUseCase {
    private final CountryPersistPort countryPersistPort;

    @Override
    public List<GetCountriesResDTO> getCountries(String continent) {
        List<Country> countries = countryPersistPort.getCountriesByContinent(continent);
        return countries.stream().map(
                country -> GetCountriesResDTO.builder()
                        .countryName(country.getCountry())
                        .countryNameEnglish(country.getCountryEnglish())
                        .continentName(country.getContinent())
                        .continentNameEnglish(country.getContinentEnglish())
                        .capitalName(country.getCapital())
                        .capitalNameEnglish(country.getCapitalEnglish())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public List<GetCountriesResDTO> getCountriesByKeyword(String keyword) {
        List<Country> countries = countryPersistPort.getCountriesByKeyword(keyword);
        return countries.stream().map(
                country -> GetCountriesResDTO.builder()
                        .countryName(country.getCountry())
                        .countryNameEnglish(country.getCountryEnglish())
                        .continentName(country.getContinent())
                        .continentNameEnglish(country.getContinentEnglish())
                        .capitalName(country.getCapital())
                        .capitalNameEnglish(country.getCapitalEnglish())
                        .build()).collect(Collectors.toList());
    }
}
