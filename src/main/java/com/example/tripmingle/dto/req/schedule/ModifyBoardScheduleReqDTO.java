package com.example.tripmingle.dto.req.schedule;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ModifyBoardScheduleReqDTO {
    List<UpdateBoardScheduleReqDTO> updateBoardScheduleReqDTOS;
    List<DeleteBoardScheduleReqDTO> deleteBoardScheduleReqDTOS;
}
