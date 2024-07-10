package com.example.tripmingle.repository;

import com.example.tripmingle.entity.ChatRoomType;
import com.example.tripmingle.entity.ChatRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {
    Optional<ChatRoomUser> findByChatRoomIdAndUserId(Long groupChatRoomId, Long userId);

    List<ChatRoomUser> findAllByChatRoomIdAndUserId(Long chatRoomId, Long userId);

    boolean existsByUserId(Long userId);

	int countById(Long oneOnOneChatRoomId);

	@Query("select count(cru.id) from ChatRoomUser cru where cru.chatRoomId = :oneOnOneChatRoomId and cru.chatRoomType = :chatRoomType")
	int countByIdAndChatRoomType(Long oneOnOneChatRoomId, ChatRoomType chatRoomType);
}
