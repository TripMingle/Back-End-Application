package com.example.tripmingle.adapter.out;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.BoardScheduleNotFoundException;
import com.example.tripmingle.dto.req.schedule.UpdateBoardScheduleReqDTO;
import com.example.tripmingle.entity.BoardSchedule;
import com.example.tripmingle.port.out.BoardSchedulePersistPort;
import com.example.tripmingle.repository.BoardScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardSchedulePersistAdapter implements BoardSchedulePersistPort {
    private final BoardScheduleRepository boardScheduleRepository;
    @Override
    public BoardSchedule saveBoardSchedule(BoardSchedule boardSchedule) {
        return boardScheduleRepository.save(boardSchedule);
    }

    @Override
    public void deleteBoardScheduleById(Long boardScheduleId) {
        BoardSchedule boardSchedule = boardScheduleRepository.findById(boardScheduleId)
                .orElseThrow(()-> new BoardScheduleNotFoundException("board schedule not found", ErrorCode.BOARD_SCHEDULE_NOT_FOUND));
        boardSchedule.delete();
    }

    @Override
    public BoardSchedule updateBoardSchedule(UpdateBoardScheduleReqDTO updateBoardScheduleReqDTO) {
        BoardSchedule boardSchedule = boardScheduleRepository.findById(updateBoardScheduleReqDTO.getBoardScheduleId())
                .orElseThrow(()-> new BoardScheduleNotFoundException("board schedule not found", ErrorCode.BOARD_SCHEDULE_NOT_FOUND));

        boardSchedule.update(updateBoardScheduleReqDTO);
        return boardSchedule;
    }

    @Override
    public List<BoardSchedule> getBoardSchedulesByBoardId(Long boardId) {
        return boardScheduleRepository.findByBoardIdOrderByDateAscNumberAsc(boardId);
    }
}
