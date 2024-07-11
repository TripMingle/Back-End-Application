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
public class GetContinentsResDTO {
	private String continentName;
	private String continentEnglishName;
	private String imageUrl;

}
