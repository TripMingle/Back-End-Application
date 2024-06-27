package com.example.tripmingle.dto.req.schedule;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ModifyUserScheduleReqDTO {
    List<UpdateUserScheduleReqDTO> updateUserScheduleReqDTOS;
    List<DeleteUserScheduleReqDTO> deleteUserScheduleReqDTOS;
}
