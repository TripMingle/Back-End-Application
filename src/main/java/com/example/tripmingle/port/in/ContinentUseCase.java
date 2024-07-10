package com.example.tripmingle.port.in;

import java.util.List;

import com.example.tripmingle.dto.res.country.GetContinentsResDTO;

public interface ContinentUseCase {
	List<GetContinentsResDTO> getContinents();
}
