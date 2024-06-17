package com.example.tripmingle.adapter.out;

import com.example.tripmingle.entity.Country;
import com.example.tripmingle.port.out.CountryPersistPort;
import com.example.tripmingle.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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
}
