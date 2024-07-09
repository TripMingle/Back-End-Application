package com.example.tripmingle.port.out;

import java.util.List;
import java.util.Optional;

import com.example.tripmingle.entity.CountryImage;

public interface CountryImagePersistPort {
	void saveImage(CountryImage countryImage);

	void deleteImageByImageUrl(String imageUrl);

	Optional<CountryImage> getPrimaryImageByCountryId(Long countryId);

	List<CountryImage> getCountryImagesByCountryName(String countryName);
}
