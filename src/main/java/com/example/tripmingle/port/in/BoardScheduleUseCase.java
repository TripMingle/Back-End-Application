package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.schedule.CreateBoardScheduleReqDTO;
import com.example.tripmingle.dto.req.schedule.DeleteBoardScheduleReqDTO;
import com.example.tripmingle.dto.req.schedule.UpdateBoardScheduleReqDTO;
import com.example.tripmingle.dto.res.schedule.BoardScheduleResDTO;
import com.example.tripmingle.dto.res.schedule.GetBoardScheduleResDTO;

import java.util.List;

public interface BoardScheduleUseCase {
    List<BoardScheduleResDTO> createBoardSchedule(Long boardId, List<CreateBoardScheduleReqDTO> createBoardScheduleReqDTOS);

    void modifyBoardSchedule(Long boardId, List<UpdateBoardScheduleReqDTO> updateBoardScheduleReqDTOS, List<DeleteBoardScheduleReqDTO> deleteBoardScheduleReqDTOS);

    GetBoardScheduleResDTO getBoardSchedule(Long boardId);
}
