package com.example.tripmingle.application.facadeService;

import com.example.tripmingle.application.service.BoardScheduleService;
import com.example.tripmingle.application.service.BoardService;
import com.example.tripmingle.application.service.UserService;
import com.example.tripmingle.common.utils.UserUtils;
import com.example.tripmingle.dto.req.schedule.CreateBoardScheduleReqDTO;
import com.example.tripmingle.dto.req.schedule.DeleteBoardScheduleReqDTO;
import com.example.tripmingle.dto.req.schedule.UpdateBoardScheduleReqDTO;
import com.example.tripmingle.dto.res.schedule.BoardScheduleResDTO;
import com.example.tripmingle.dto.res.schedule.GetBoardScheduleResDTO;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BoardSchedule;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.in.BoardScheduleUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleFacadeService implements BoardScheduleUseCase {
    private final BoardScheduleService boardScheduleService;
    private final BoardService boardService;
    private final UserService userService;
    private final UserUtils userUtils;
    @Override
    public List<BoardScheduleResDTO> createBoardSchedule(Long boardId, List<CreateBoardScheduleReqDTO> createBoardScheduleReqDTOS) {
        Board board = boardService.getBoardById(boardId);
        User currentUser = userService.getCurrentUser();
        List<BoardSchedule> boardSchedules = boardScheduleService.createBoardSchedule(board,currentUser,createBoardScheduleReqDTOS);

        return boardSchedules.stream().map(boardSchedule -> {
            return BoardScheduleResDTO.builder()
                    .boardId(boardSchedule.getBoard().getId())
                    .boardScheduleId(boardSchedule.getId())
                    .date(boardSchedule.getDate())
                    .placeName(boardSchedule.getPlaceName())
                    .number(boardSchedule.getNumber())
                    .pointX(boardSchedule.getPointX())
                    .pointY(boardSchedule.getPointY())
                    .googlePlaceId(boardSchedule.getGooglePlaceId())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public void modifyBoardSchedule(Long boardId, List<UpdateBoardScheduleReqDTO> updateBoardScheduleReqDTOS, List<DeleteBoardScheduleReqDTO> deleteBoardScheduleReqDTOS) {
        Board board = boardService.getBoardById(boardId);
        User currentUser = userService.getCurrentUser();
        boardScheduleService.deleteBoardSchedule(board,currentUser,deleteBoardScheduleReqDTOS);
        boardScheduleService.updateBoardSchedule(board,currentUser,updateBoardScheduleReqDTOS);
    }

    @Override
    public GetBoardScheduleResDTO getBoardSchedule(Long boardId) {
        User currentUser = userService.getCurrentUser();
        Board board = boardService.getBoardById(boardId);
        List<BoardScheduleResDTO> boardScheduleResDTOList
                = boardScheduleService.getBoardSchedulesByBoardId(boardId).stream()
                .map(boardSchedule ->{
            return BoardScheduleResDTO.builder()
                    .boardId(board.getId())
                    .boardScheduleId(boardSchedule.getId())
                    .date(boardSchedule.getDate())
                    .placeName(boardSchedule.getPlaceName())
                    .number(boardSchedule.getNumber())
                    .pointX(boardSchedule.getPointX())
                    .pointY(boardSchedule.getPointY())
                    .googlePlaceId(boardSchedule.getGooglePlaceId())
                    .build();
        }).collect(Collectors.toList());
        return GetBoardScheduleResDTO.builder()
                .isMyBoard(currentUser.getId().equals(board.getUser().getId()))
                .boardScheduleResDTOList(boardScheduleResDTOList)
                .build();
    }
}
