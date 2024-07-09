package com.example.tripmingle.application.facadeService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.tripmingle.application.service.CountryImageService;
import com.example.tripmingle.application.service.CountryService;
import com.example.tripmingle.application.service.S3Service;
import com.example.tripmingle.dto.res.country.GetCountriesResDTO;
import com.example.tripmingle.dto.res.country.UploadCountryImageResDTO;
import com.example.tripmingle.entity.Country;
import com.example.tripmingle.port.in.CountryUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CountryFacadeService implements CountryUseCase {
	private final CountryService countryService;
	private final S3Service s3Service;
	private final CountryImageService countryImageService;

	@Override
	public List<GetCountriesResDTO> getCountries(String continent) {
		List<Country> countries = countryService.getCountriesByContinent(continent);
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
		List<Country> countries = countryService.getCountriesByKeyword(keyword);
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
	public UploadCountryImageResDTO uploadCountryImage(String countryName, MultipartFile image) {
		String imageUrl = s3Service.uploadCountryImage(image);
		Country country = countryService.getCountryByCountryName(countryName);
		countryImageService.saveImage(country, imageUrl);
		return UploadCountryImageResDTO.builder().imageUrl(imageUrl).build();
	}

}
