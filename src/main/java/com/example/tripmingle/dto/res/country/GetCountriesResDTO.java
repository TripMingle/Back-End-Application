package com.example.tripmingle.dto.res.country;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCountriesResDTO {
	private String countryName;
	private String continentName;
	private String capitalName;

	private String countryNameEnglish;
	private String continentNameEnglish;
	private String capitalNameEnglish;

	private String primaryImageUrl;

}
