package com.example.tripmingle.entity;

import com.example.tripmingle.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted = false")
public class OneOnOneChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user1_id", referencedColumnName = "id")
    private User user1;

    @OneToOne
    @JoinColumn(name = "user2_id", referencedColumnName = "id")
    private User user2;

    @Builder
    public OneOnOneChatRoom(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }
}