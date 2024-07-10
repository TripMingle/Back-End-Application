package com.example.tripmingle.port.out;

import java.util.List;

import com.example.tripmingle.entity.Continent;

public interface ContinentPersistPort {
	Continent getContinentByContinentName(String continent);

	List<Continent> getAllContinent();

}
