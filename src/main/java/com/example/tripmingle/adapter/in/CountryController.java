package com.example.tripmingle.adapter.in;

import com.example.tripmingle.common.result.ResultCode;
import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.res.GetCountriesResDTO;
import com.example.tripmingle.port.in.CountryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/country")
@RequiredArgsConstructor
public class CountryController {
    private final CountryUseCase countryUseCase;

    //검색할때 확인하는 로직
    @GetMapping("/search/{keyword}")
    public ResponseEntity<ResultResponse> getAutoComplete(@PathVariable(value = "keyword") String keyword){
        List<GetCountriesResDTO> getCountriesDTO = countryUseCase.getCountriesByKeyword(keyword);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_COUNTRIES_BY_KEYWORD_SUCCESS, getCountriesDTO));
    }

    //대륙에 포함되는 나라 전부 조회
    @GetMapping("/{continent}")
    public ResponseEntity<ResultResponse> getCountries(@PathVariable(value = "continent")String continent){
        List<GetCountriesResDTO> getCountriesResDTO = countryUseCase.getCountries(continent);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_COUNTRIES_BY_CONTINENT_SUCCESS, getCountriesResDTO));
    }



}
