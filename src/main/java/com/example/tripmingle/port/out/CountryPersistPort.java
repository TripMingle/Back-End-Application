package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.Country;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CountryPersistPort {

    List<Country> getCountriesByContinent(String continent);

    List<Country> getCountriesByKeyword(String keyword);
}
