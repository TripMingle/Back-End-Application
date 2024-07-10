package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.Continent;

public interface ContinentPersistPort {
	Continent getContinentByContinentName(String continent);
}
