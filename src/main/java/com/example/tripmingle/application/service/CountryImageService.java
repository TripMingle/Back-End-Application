package com.example.tripmingle.application.service;

import org.springframework.stereotype.Service;

import com.example.tripmingle.entity.Country;
import com.example.tripmingle.entity.CountryImage;
import com.example.tripmingle.port.out.CountryImagePersistPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CountryImageService {
	private CountryImagePersistPort countryImagePersistPort;

	public void saveImage(Country country, String imageUrl) {
		CountryImage countryImage = CountryImage.builder()
			.country(country)
			.imageUrl(imageUrl)
			.build();
		countryImagePersistPort.saveImage(countryImage);
	}
}
