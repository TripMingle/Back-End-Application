package com.example.tripmingle.repository;

import java.util.Optional;

import com.example.tripmingle.entity.OneOnOneChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OneOnOneChatRoomRepository extends JpaRepository<OneOnOneChatRoom, Long> {

	boolean existsByUser1IdAndUser2Id(Long currentUserId, Long contactUserId);

	Optional<OneOnOneChatRoom> findByUser1IdAndUser2Id(Long currentUserId, Long contactUserId);
}
