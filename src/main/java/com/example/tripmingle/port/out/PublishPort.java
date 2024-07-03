package com.example.tripmingle.port.out;

import com.example.tripmingle.dto.etc.UserPersonalityIdPublishDTO;

import java.util.concurrent.CompletableFuture;

public interface PublishPort {
    CompletableFuture<String> addUserPublish(UserPersonalityIdPublishDTO userPersonalityIdPublishDTO);
}
