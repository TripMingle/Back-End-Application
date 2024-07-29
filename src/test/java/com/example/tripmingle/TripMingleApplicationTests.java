package com.example.tripmingle;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.example.tripmingle.entity.UserSchedule;
import com.example.tripmingle.repository.UserScheduleRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TripMingleApplicationTests {
	@Autowired
	private BoardRepository boardRepository;

	@Test
	@DisplayName("테스트")
	@Transactional
	void test() {
	}

}


