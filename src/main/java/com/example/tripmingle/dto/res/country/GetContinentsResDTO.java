package com.example.tripmingle.dto.res.country;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class GetContinentsResDTO {
	private String continentName;
	private String continentEnglishName;
	private String imageUrl;

	public GetContinentsResDTO(String continentName, String continentEnglishName, String imageUrl) {
		this.continentName = continentName;
		this.continentEnglishName = continentEnglishName;
		this.imageUrl = imageUrl;
	}
}
