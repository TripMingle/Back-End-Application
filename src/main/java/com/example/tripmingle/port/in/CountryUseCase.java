package com.example.tripmingle.port.in;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.tripmingle.dto.res.country.GetCountriesResDTO;
import com.example.tripmingle.dto.res.country.GetCountryInfoDTO;
import com.example.tripmingle.dto.res.country.UploadCountryImageResDTO;

public interface CountryUseCase {
	List<GetCountriesResDTO> getCountries(String continent);

	List<GetCountriesResDTO> getCountriesByKeyword(String keyword);

	UploadCountryImageResDTO uploadCountryImage(String countryName, MultipartFile image, boolean isPrimary);

	void deleteCountryImage(String imageUrl);

	GetCountryInfoDTO getCountryInfo(String countryName);
}
