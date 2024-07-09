package com.example.tripmingle.dto.etc;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPersonalityReCalculatePublishDTO {
	private Long userPersonalityId;
	private String messageId;
}
