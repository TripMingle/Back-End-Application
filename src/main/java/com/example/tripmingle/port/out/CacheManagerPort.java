package com.example.tripmingle.port.out;

import java.util.List;

import com.example.tripmingle.dto.res.country.GetContinentsResDTO;
import com.example.tripmingle.dto.res.country.GetCountriesResDTO;

public interface CacheManagerPort {
	List<Long> getSimilarUsersByUserId(Long userPersonalityId);

	boolean getDeletedBit(Long userId);

	List<GetContinentsResDTO> getContinentsInCache();

	void setContinentsAtCache(List<GetContinentsResDTO> collect);

	List<GetCountriesResDTO> getCountriesAtCache(String continent);

	void setCountriesAtCache(String continent, List<GetCountriesResDTO> getCountriesResDTOS);
}
