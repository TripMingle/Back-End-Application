package com.example.tripmingle.adapter.out;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.ContinentNotFoundException;
import com.example.tripmingle.entity.Continent;
import com.example.tripmingle.port.out.ContinentPersistPort;
import com.example.tripmingle.repository.ContinentRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContinentPersistAdapter implements ContinentPersistPort {
	private final ContinentRepository continentRepository;

	@Override
	public Continent getContinentByContinentName(String continent) {
		return continentRepository.findContinentByContinentName(continent)
			.orElseThrow(() -> new ContinentNotFoundException("continent not found", ErrorCode.CONTINENT_NOT_FOUND));
	}

	@Override
	public List<Continent> getAllContinent() {
		return continentRepository.findAll();
	}

}
