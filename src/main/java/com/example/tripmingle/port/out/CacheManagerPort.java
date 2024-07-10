package com.example.tripmingle.port.out;

import java.util.List;

import com.example.tripmingle.dto.res.country.GetContinentsResDTO;

public interface CacheManagerPort {
	List<Long> getSimilarUsersByUserId(Long userPersonalityId);

	boolean getDeletedBit(Long userId);

	List<GetContinentsResDTO> getContinentsInCache();

	void setContinentsAtCache(List<GetContinentsResDTO> collect);
}
