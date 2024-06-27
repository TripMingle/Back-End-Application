package com.example.tripmingle.adapter.in;

import com.example.tripmingle.common.result.ResultCode;
import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.req.schedule.*;
import com.example.tripmingle.dto.res.schedule.CreateUserTripResDTO;
import com.example.tripmingle.dto.res.schedule.GetUserScheduleResDTO;
import com.example.tripmingle.dto.res.schedule.GetUserTripResDTO;
import com.example.tripmingle.dto.res.schedule.UserScheduleResDTO;
import com.example.tripmingle.port.in.UserScheduleUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/schedule")
@RequiredArgsConstructor
public class UserScheduleController {
    private final UserScheduleUseCase userScheduleUseCase;
    @PostMapping()
    //유저 여행일정 생성 (대륙,나라,기간)
    public ResponseEntity<ResultResponse> createUserTrip(@RequestBody CreateUserTripReqDTO createUserTripReqDTO){
        CreateUserTripResDTO createUserTripResDTO = userScheduleUseCase.createUserTrip(createUserTripReqDTO);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CREATE_USER_TRIP_SUCCESS, createUserTripResDTO));
    }

    @PostMapping("/{user-trip-id}")
    //유저 세부일정 추가
    public ResponseEntity<ResultResponse> createUserSchedule(@PathVariable(value = "user-trip-id") Long userTripId,
                                                            @RequestBody List<CreateUserScheduleReqDTO> createUserScheduleReqDTOS){
        List<UserScheduleResDTO> userSchedules = userScheduleUseCase.createUserSchedule(userTripId, createUserScheduleReqDTOS);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CREATE_USER_SCHEDULE_SUCCESS, userSchedules));
    }

    @DeleteMapping("/{user-trip-id}")
    //유저 여행일정 삭제
    public ResponseEntity<ResultResponse> deleteUserTrip(@PathVariable(value = "user-trip-id") Long userTripId){
        userScheduleUseCase.deleteUserTrip(userTripId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_USER_TRIP_SUCCESS));
    }

    @PostMapping("/{user-trip-id}/modify")
    //유저 세부일정 삭제&수정
    public ResponseEntity<ResultResponse> modifyUserSchedule(@PathVariable(value = "user-trip-id") Long userTripId,
                                                             @RequestBody ModifyUserScheduleReqDTO modifyUserScheduleReqDTO){
        userScheduleUseCase.modifyUserSchedule(userTripId,modifyUserScheduleReqDTO.getUpdateUserScheduleReqDTOS(), modifyUserScheduleReqDTO.getDeleteUserScheduleReqDTOS());
        return ResponseEntity.ok(ResultResponse.of(ResultCode.MODIFY_BOARD_SCHEDULE_SUCCESS));
    }

    @GetMapping
    //유저 여행일정 조회
    public ResponseEntity<ResultResponse> getUserTrip(){
        List<GetUserTripResDTO> getUserTripResDTOS = userScheduleUseCase.getUserTrip();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_USER_TRIP_SUCCESS, getUserTripResDTOS));
    }

    @GetMapping("/{user-trip-id}")
    //세부일정조회 -> 그냥 전부 리스트로 주면됨 (day, 순서별로 정렬하면 더좋음)
    public ResponseEntity<ResultResponse> getUserSchedule(@PathVariable(value = "user-trip-id") Long userTripId){
        GetUserScheduleResDTO getUserScheduleResDTO = userScheduleUseCase.getUserSchedule(userTripId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_USER_SCHEDULE_SUCCESS, getUserScheduleResDTO));
    }

    //내 유저 일정들 조회하기 (country 받고, List<boardSchedule> 리턴) -> 내일정에서 불러오는 로직

    //게시판 일정에서 내 일정으로 옮기기 -> 이것도 수정의 일부이므로 여쭤본 다음에 구현

}
