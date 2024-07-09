package com.example.tripmingle.adapter.in;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.tripmingle.common.result.ResultCode;
import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.res.country.GetCountriesResDTO;
import com.example.tripmingle.dto.res.country.UploadCountryImageResDTO;
import com.example.tripmingle.port.in.CountryUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "나라")
@RestController
@RequestMapping("/country")
@RequiredArgsConstructor
public class CountryController {
	private final CountryUseCase countryUseCase;

	@Operation(summary = "자동완성")
	//검색할때 확인하는 로직
	@GetMapping("/search/{keyword}")
	public ResponseEntity<ResultResponse> getAutoComplete(@PathVariable(value = "keyword") String keyword) {
		List<GetCountriesResDTO> getCountriesDTO = countryUseCase.getCountriesByKeyword(keyword);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_COUNTRIES_BY_KEYWORD_SUCCESS, getCountriesDTO));
	}

	@Operation(summary = "대륙별 나라조회")
	//대륙에 포함되는 나라 전부 조회
	@GetMapping("/{continent}")
	public ResponseEntity<ResultResponse> getCountries(@PathVariable(value = "continent") String continent) {
		List<GetCountriesResDTO> getCountriesResDTO = countryUseCase.getCountries(continent);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_COUNTRIES_BY_CONTINENT_SUCCESS, getCountriesResDTO));
	}

	@Operation(summary = "국가 사진 업로드")
	@PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResultResponse> uploadCountryImage(
		@RequestPart("countryName") String countryName,
		@RequestPart("image") MultipartFile image) {
		UploadCountryImageResDTO uploadCountryImageResDTO = countryUseCase.uploadCountryImage(countryName, image);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.UPLOAD_COUNTRY_IMAGE_SUCCESS, uploadCountryImageResDTO));
	}

}
