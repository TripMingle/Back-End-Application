package com.example.tripmingle.application.service;

import com.example.tripmingle.common.utils.UserUtils;
import com.example.tripmingle.dto.req.schedule.CreateBoardScheduleReqDTO;
import com.example.tripmingle.dto.req.schedule.DeleteBoardScheduleReqDTO;
import com.example.tripmingle.dto.req.schedule.UpdateBoardScheduleReqDTO;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BoardSchedule;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.BoardSchedulePersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardScheduleService {
    private final BoardSchedulePersistPort boardSchedulePersistPort;
    private final UserUtils userUtils;
    public List<BoardSchedule> createBoardSchedule(Board board, User currentUser, List<CreateBoardScheduleReqDTO> createBoardScheduleReqDTOS) {
        userUtils.validateMasterUser(board.getUser().getId(),currentUser.getId());

        return createBoardScheduleReqDTOS.stream()
                .map(DTO -> {
                    BoardSchedule boardSchedule = BoardSchedule.builder()
                            .board(board)
                            .date(DTO.getDate())
                            .placeName(DTO.getPlaceName())
                            .number(DTO.getNumber())
                            .pointX(DTO.getPointX())
                            .pointY(DTO.getPointY())
                            .googlePlaceId(DTO.getGooglePlaceId())
                            .build();
                    return boardSchedulePersistPort.saveBoardSchedule(boardSchedule);
                }).collect(Collectors.toList());
    }

    public void deleteBoardSchedule(Board board, User currentUser, List<DeleteBoardScheduleReqDTO> deleteBoardScheduleReqDTOS) {
        userUtils.validateMasterUser(board.getUser().getId(),currentUser.getId());
        deleteBoardScheduleReqDTOS.forEach(
                DTO -> boardSchedulePersistPort.deleteBoardScheduleById(DTO.getBoardScheduleId()));
    }

    public void updateBoardSchedule(Board board, User currentUser, List<UpdateBoardScheduleReqDTO> updateBoardScheduleReqDTOS) {
        userUtils.validateMasterUser(board.getUser().getId(),currentUser.getId());
        updateBoardScheduleReqDTOS.forEach( DTO-> boardSchedulePersistPort.updateBoardSchedule(DTO));
    }

    public List<BoardSchedule> getBoardSchedulesByBoardId(Long boardId) {
        return boardSchedulePersistPort.getBoardSchedulesByBoardId(boardId);
    }
}
