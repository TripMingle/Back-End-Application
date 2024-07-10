package com.example.tripmingle.application.facadeService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.tripmingle.application.service.ContinentService;
import com.example.tripmingle.dto.res.country.GetContinentsResDTO;
import com.example.tripmingle.entity.Continent;
import com.example.tripmingle.port.in.ContinentUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContinentFacadeService implements ContinentUseCase {
	private final ContinentService continentService;

	@Override
	public List<GetContinentsResDTO> getContinents() {

		List<GetContinentsResDTO> getContinentsResDTOS = continentService.getContinentsAtCache();
		if (getContinentsResDTOS != null) {
			return getContinentsResDTOS;
		}

		List<Continent> continents = continentService.getAllContinent();

		continentService.setContinentsAtCache(continents);
		
		return continents.stream().map(continent ->
			GetContinentsResDTO.builder()
				.continentName(continent.getContinentName())
				.continentEnglishName(continent.getContinentEnglishName())
				.imageUrl(continent.getImageUrl())
				.build()).collect(Collectors.toList());
	}
}
