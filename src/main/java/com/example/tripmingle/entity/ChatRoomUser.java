package com.example.tripmingle.entity;

import com.example.tripmingle.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long chatRoomId;

    @Enumerated(value = EnumType.STRING)
    private ChatRoomType chatRoomType;

    @Builder
    public ChatRoomUser(User user, Long chatRoomId, ChatRoomType chatRoomType) {
        this.user = user;
        this.chatRoomId = chatRoomId;
        this.chatRoomType = chatRoomType;
    }

    public void exitChatRoom() {
        delete();
    }
}
