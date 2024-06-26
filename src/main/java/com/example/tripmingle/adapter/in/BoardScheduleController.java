package com.example.tripmingle.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board/schedule")
@RequiredArgsConstructor
public class BoardScheduleController {

    //게시판 일정 생성 (게시물id-> 대륙,나라,기간 등 가져오기, List<장소,날짜,순서,좌표>

    //게시물 일정 수정 -> 일단 구현하지 않고 멘토님께 여쭤본 다음에 구현

    //게시물 일정 삭제

    //일정조회 -> 그냥 전부 리스트로 주면됨 (day, 순서별로 정렬하면 더좋음)

    //내가 참여하고있는 게시물 일정들 조회하기 (country 받아야하고 board, List<boardSchedule> 리턴) -> 내일정에서 불러오는 로직

    //내 일정에서 게시판 일정으로 옮기기 이것도 수정의 일부이므로 여쭤본 다음에 구현

    //지도에서 조회 (나라, 기간에 대한 필터링 + 좌표에 대한 필터링이 있다, 이미 지난 일정은 조회하지 않도록 하는 로직 필요) -> 인덱싱 필요
    //is_expired를 하면 안될거같음. 그냥 쿼리문에 날짜비교쿼리를 포함시키는게 좋을듯?
}
