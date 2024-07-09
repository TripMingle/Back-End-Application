package com.example.tripmingle.application.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.tripmingle.entity.Country;
import com.example.tripmingle.entity.CountryImage;
import com.example.tripmingle.port.out.CountryImagePersistPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CountryImageService {
	private final CountryImagePersistPort countryImagePersistPort;

	public void saveImage(Country country, String imageUrl, boolean isPrimary) {
		CountryImage countryImage = CountryImage.builder()
			.country(country)
			.imageUrl(imageUrl)
			.isPrimary(isPrimary)
			.build();
		countryImagePersistPort.saveImage(countryImage);
	}

	public void deleteImageByImageUrl(String imageUrl) {
		countryImagePersistPort.deleteImageByImageUrl(imageUrl);
	}

	public Optional<CountryImage> getPrimaryImageByCountryId(Long countryId) {
		return countryImagePersistPort.getPrimaryImageByCountryId(countryId);
	}

	public String getRandomCountryImage(String countryName) {
		List<CountryImage> countryImages = countryImagePersistPort.getCountryImagesByCountryName(countryName);
		if (countryImages.isEmpty()) {
			return "";
		}
		Random random = new Random();
		int randomIndex = random.nextInt(countryImages.size());
		return countryImages.get(randomIndex).getImageUrl();
	}
}
