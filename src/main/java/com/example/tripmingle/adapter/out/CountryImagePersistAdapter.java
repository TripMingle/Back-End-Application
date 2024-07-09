package com.example.tripmingle.adapter.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.CountryImageNotFoundException;
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

	@Override
	public void deleteImageByImageUrl(String imageUrl) {
		CountryImage countryImage = countryImageRepository.findCountryImageByImageUrl(imageUrl)
			.orElseThrow(
				() -> new CountryImageNotFoundException("country image not found", ErrorCode.COUNTRY_IMAGE_NOT_FOUND));
		countryImageRepository.delete(countryImage);
	}

	@Override
	public Optional<CountryImage> getPrimaryImageByCountryId(Long countryId) {
		return countryImageRepository.getPrimaryImageByCountryId(countryId);
	}

	@Override
	public List<CountryImage> getCountryImagesByCountryName(String countryName) {
		return countryImageRepository.findCountryImageByCountryName(countryName);
	}

}
