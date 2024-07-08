package com.example.tripmingle.port.out;

import java.util.List;

public interface MatchingPort {
	List<Long> getSimilarUsersByUserId(Long userPersonalityId);

	boolean getDeletedBit(Long userId);

}
