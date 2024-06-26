package com.example.tripmingle.port.out;

import com.example.tripmingle.dto.req.schedule.UpdateBoardScheduleReqDTO;
import com.example.tripmingle.entity.BoardSchedule;

import java.util.List;

public interface BoardSchedulePersistPort {
    BoardSchedule saveBoardSchedule(BoardSchedule boardSchedule);

    void deleteBoardScheduleById(Long boardScheduleId);

    BoardSchedule updateBoardSchedule(UpdateBoardScheduleReqDTO dto);

    List<BoardSchedule> getBoardSchedulesByBoardId(Long boardId);
}
