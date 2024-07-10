package com.example.tripmingle.application.service;

import org.springframework.stereotype.Service;

import com.example.tripmingle.port.out.ContinentPersistPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContinentService {
	private final ContinentPersistPort continentPersistPort;

	public String getContinentImage(String continent) {
		return continentPersistPort.getContinentByContinentName(continent).getImageUrl();
	}
}
