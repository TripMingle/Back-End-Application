package com.example.tripmingle.dto.etc;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteUserPersonalityPublishDTO {
	private String messageId;
	private Long userPersonalityId;
}
