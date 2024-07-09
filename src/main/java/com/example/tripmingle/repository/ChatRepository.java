package com.example.tripmingle.repository;

import com.example.tripmingle.entity.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<ChatMessage, String> {

    Slice<ChatMessage> findAllByChatRoomId(Long chatRoomId, Pageable pageable);
    
}
