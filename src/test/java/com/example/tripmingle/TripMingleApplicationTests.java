package com.example.tripmingle;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.example.tripmingle.entity.UserSchedule;
import com.example.tripmingle.repository.UserScheduleRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TripMingleApplicationTests {
	@Autowired
	private UserScheduleRepository userScheduleRepository;

	@Test
	@DisplayName("테스트")
	@Transactional
	void test() {
		UserSchedule userSchedule = userScheduleRepository.findById(5L).get();
		userSchedule.updateNumber(22);
		userScheduleRepository.flush();
		UserSchedule userSchedule1 = userScheduleRepository.findByNumber(23145);
	}

}


