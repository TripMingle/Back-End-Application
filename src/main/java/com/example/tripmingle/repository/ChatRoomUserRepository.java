package com.example.tripmingle.repository;

import com.example.tripmingle.entity.ChatRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {
    Optional<ChatRoomUser> findByChatRoomIdAndUserId(Long groupChatRoomId, Long userId);

    List<ChatRoomUser> findAllByChatRoomIdAndUserId(Long chatRoomId, Long userId);

    boolean existsByUserId(Long userId);
}
