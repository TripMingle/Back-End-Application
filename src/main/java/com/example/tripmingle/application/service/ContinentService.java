package com.example.tripmingle.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.tripmingle.dto.res.country.GetContinentsResDTO;
import com.example.tripmingle.entity.Continent;
import com.example.tripmingle.port.out.CacheManagerPort;
import com.example.tripmingle.port.out.ContinentPersistPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContinentService {
	private final ContinentPersistPort continentPersistPort;
	private final CacheManagerPort cacheManagerPort;

	public String getContinentImage(String continent) {
		return continentPersistPort.getContinentByContinentName(continent).getImageUrl();
	}

	public List<GetContinentsResDTO> getContinentsAtCache() {
		return cacheManagerPort.getContinentsInCache();
	}

	public List<Continent> getAllContinent() {
		return continentPersistPort.getAllContinent();
	}

	public void setContinentsAtCache(List<Continent> continents) {
		cacheManagerPort.setContinentsAtCache(continents.stream()
			.map(continent -> GetContinentsResDTO.builder()
				.continentName(continent.getContinentName())
				.continentEnglishName(continent.getContinentEnglishName())
				.imageUrl(continent.getImageUrl())
				.build()).collect(Collectors.toList()));
	}
}
