package com.example.tripmingle.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="board_id", nullable = false)
    private Board board;

    private boolean isActive = true;

    public void toggleBookMark(){
        this.isActive = !this.isActive;
    }
}
