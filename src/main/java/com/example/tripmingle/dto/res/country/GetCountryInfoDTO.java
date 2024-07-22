package com.example.tripmingle.dto.res.country;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetCountryInfoDTO {
	private String countryImageUrl;
	private String continentName;
	private String countryName;
	private String continentEnglishName;
	private String countryEnglishName;
}
