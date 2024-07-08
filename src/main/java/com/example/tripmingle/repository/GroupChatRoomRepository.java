package com.example.tripmingle.repository;

import com.example.tripmingle.entity.GroupChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupChatRoomRepository extends JpaRepository<GroupChatRoom, Long> {
    boolean existsByBoardIdAndUserId(Long boardId, Long userId);
}
