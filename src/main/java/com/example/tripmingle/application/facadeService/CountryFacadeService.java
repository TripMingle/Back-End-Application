package com.example.tripmingle.application.facadeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.tripmingle.application.service.ContinentService;
import com.example.tripmingle.application.service.CountryImageService;
import com.example.tripmingle.application.service.CountryService;
import com.example.tripmingle.application.service.S3Service;
import com.example.tripmingle.dto.res.country.GetCountriesResDTO;
import com.example.tripmingle.dto.res.country.UploadCountryImageResDTO;
import com.example.tripmingle.entity.Country;
import com.example.tripmingle.entity.CountryImage;
import com.example.tripmingle.port.in.CountryUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CountryFacadeService implements CountryUseCase {
	private final CountryService countryService;
	private final S3Service s3Service;
	private final CountryImageService countryImageService;
	private final ContinentService continentService;

	@Override
	public List<GetCountriesResDTO> getCountries(String continent) {
		List<GetCountriesResDTO> getCountriesResDTOS = countryService.getCountriesAtCache(continent);
		if (getCountriesResDTOS != null) {
			return getCountriesResDTOS;
		}

		List<Country> countries = countryService.getCountriesByContinent(continent);
		getCountriesResDTOS = countries.stream().map(
			country -> {
				Optional<CountryImage> countryImageOptional = countryImageService.getPrimaryImageByCountryId(
					country.getId());
				String url = countryImageOptional.isPresent() ?
					countryImageOptional.get().getImageUrl() :
					continentService.getContinentImage(country.getContinent());

				return GetCountriesResDTO.builder()
					.countryName(country.getCountry())
					.countryNameEnglish(country.getCountryEnglish())
					.continentName(country.getContinent())
					.continentNameEnglish(country.getContinentEnglish())
					.capitalName(country.getCapital())
					.capitalNameEnglish(country.getCapitalEnglish())
					.primaryImageUrl(url)
					.build();
			}).collect(Collectors.toList());

		countryService.setCountryAtCache(continent, getCountriesResDTOS);

		return getCountriesResDTOS;
	}

	public List<GetCountriesResDTO> getCountriesWithoutCache(String continent) {

		List<Country> countries = countryService.getCountriesByContinent(continent);
		List<GetCountriesResDTO> getCountriesResDTOS = countries.stream().map(
			country -> {
				Optional<CountryImage> countryImageOptional = countryImageService.getPrimaryImageByCountryId(
					country.getId());
				String url = countryImageOptional.isPresent() ?
					countryImageOptional.get().getImageUrl() :
					continentService.getContinentImage(country.getContinent());

				return GetCountriesResDTO.builder()
					.countryName(country.getCountry())
					.countryNameEnglish(country.getCountryEnglish())
					.continentName(country.getContinent())
					.continentNameEnglish(country.getContinentEnglish())
					.capitalName(country.getCapital())
					.capitalNameEnglish(country.getCapitalEnglish())
					.primaryImageUrl(url)
					.build();
			}).collect(Collectors.toList());

		countryService.setCountryAtCache(continent, getCountriesResDTOS);

		return getCountriesResDTOS;
	}

	@Override
	public List<GetCountriesResDTO> getCountriesByKeyword(String keyword) {
		List<Country> countries = countryService.getCountriesByKeyword(keyword);
		return countries.stream().map(
			country -> {
				Optional<CountryImage> countryImageOptional = countryImageService.getPrimaryImageByCountryId(
					country.getId());
				String url = countryImageOptional.isPresent() ?
					countryImageOptional.get().getImageUrl() :
					continentService.getContinentImage(country.getContinent());

				return GetCountriesResDTO.builder()
					.countryName(country.getCountry())
					.countryNameEnglish(country.getCountryEnglish())
					.continentName(country.getContinent())
					.continentNameEnglish(country.getContinentEnglish())
					.capitalName(country.getCapital())
					.capitalNameEnglish(country.getCapitalEnglish())
					.primaryImageUrl(url)
					.build();
			}).collect(Collectors.toList());
	}

	@Override
	public UploadCountryImageResDTO uploadCountryImage(String countryName, MultipartFile image, boolean isPrimary) {
		String imageUrl = s3Service.uploadImage(image);
		Country country = countryService.getCountryByCountryName(countryName);
		countryImageService.saveImage(country, imageUrl, isPrimary);
		return UploadCountryImageResDTO.builder().imageUrl(imageUrl).build();
	}

	@Override
	public void deleteCountryImage(String imageUrl) {
		countryImageService.deleteImageByImageUrl(imageUrl);
		s3Service.deleteCountryImage(imageUrl);
	}

}
