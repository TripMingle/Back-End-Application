package com.example.tripmingle.adapter.in;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/schedule")
public class UserScheduleController {
    //유저 일정 생성 (List<대륙,나라,기간,장소,날짜,순서,좌표>

    //유저 일정 수정 -> 일단 구현하지 않고 멘토님께 여쭤본 다음에 구현

    //유저 일정 삭제

    //일정조회 -> 그냥 전부 리스트로 주면됨 (day, 순서별로 정렬하면 더좋음)

    //내 유저 일정들 조회하기 (country 받고, List<boardSchedule> 리턴) -> 내일정에서 불러오는 로직

    //게시판 일정에서 내 일정으로 옮기기 -> 이것도 수정의 일부이므로 여쭤본 다음에 구현

}
