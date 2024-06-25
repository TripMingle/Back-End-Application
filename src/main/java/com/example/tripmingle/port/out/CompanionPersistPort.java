package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.Companion;
import com.example.tripmingle.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanionPersistPort {

    public void saveCompanion(Companion companion);

    Companion getCompanionByBoardAndUser(Board board, User currentUser);

    void deleteCompanion(Companion companion);

    List<Companion> getCompanionsByBoardId(Long boardId);

    Page<Companion> getCompanionsByUserId(Long id, Pageable pageable);
}
