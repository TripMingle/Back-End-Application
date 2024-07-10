package com.example.tripmingle.port.out;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.Companion;
import com.example.tripmingle.entity.User;

public interface CompanionPersistPort {

	public void saveCompanion(Companion companion);

	Companion getCompanionByBoardAndUser(Board board, User currentUser);

	void deleteCompanion(Companion companion);

	List<Companion> getCompanionsByBoardId(Long boardId);

	Page<Companion> getCompanionsByUserId(Long userId, Pageable pageable);

	boolean existsByUserIdAndBoardId(Long userId, Long boardId);

	void deleteCompanionByBoardId(Long boardId);
}
