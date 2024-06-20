package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.res.count.GetCountriesResDTO;

import java.util.List;

public interface CountryUseCase {
    List<GetCountriesResDTO> getCountries(String continent);

    List<GetCountriesResDTO> getCountriesByKeyword(String keyword);
}
