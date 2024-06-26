package com.example.tripmingle.repository;

import com.example.tripmingle.entity.BoardSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardScheduleRepository extends JpaRepository<BoardSchedule,Long> {
    List<BoardSchedule> findByBoardIdOrderByDateAscNumberAsc(Long boardId);
}
