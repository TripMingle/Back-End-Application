package com.example.tripmingle.adapter.out;

import org.springframework.stereotype.Component;

import com.example.tripmingle.entity.CountryImage;
import com.example.tripmingle.port.out.CountryImagePersistPort;
import com.example.tripmingle.repository.CountryImageRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CountryImagePersistAdapter implements CountryImagePersistPort {
	private final CountryImageRepository countryImageRepository;

	@Override
	public void saveImage(CountryImage countryImage) {
		countryImageRepository.save(countryImage);
	}
}
