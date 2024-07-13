package com.example.tripmingle.port.out;

import java.util.concurrent.CompletableFuture;

import com.example.tripmingle.dto.etc.DeleteUserPersonalityPublishDTO;
import com.example.tripmingle.dto.etc.MatchingBoardPublishDTO;
import com.example.tripmingle.dto.etc.UserPersonalityIdPublishDTO;
import com.example.tripmingle.dto.etc.UserPersonalityReCalculatePublishDTO;

public interface PublishPort {
	CompletableFuture<String> addUserPublish(UserPersonalityIdPublishDTO userPersonalityIdPublishDTO);

	CompletableFuture<String> reCalculateUserPersonality(
		UserPersonalityReCalculatePublishDTO userPersonalityReCalculatePublishDTO);

	CompletableFuture<String> deleteUserPersonality(DeleteUserPersonalityPublishDTO deleteUserPersonalityPublishDTO);

	CompletableFuture<String> matchingBoard(MatchingBoardPublishDTO matchingBoardPublishDTO);
}
