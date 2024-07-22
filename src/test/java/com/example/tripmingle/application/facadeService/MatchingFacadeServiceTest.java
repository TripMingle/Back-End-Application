package com.example.tripmingle.application.facadeService;

import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.tripmingle.application.service.UserPersonalityService;
import com.example.tripmingle.application.service.UserService;
import com.example.tripmingle.dto.req.matching.PostUserPersonalityReqDTO;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.entity.UserPersonality;
import com.example.tripmingle.port.out.UserPersonalityPersistPort;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class MatchingFacadeServiceTest {

	@Autowired
	private MatchingFacadeService matchingFacadeService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserPersonalityService userPersonalityService;
	@Autowired
	private UserPersonalityPersistPort userPersonalityPersistPort;

	@Test
	@DirtiesContext
	void matchingWithCacheTest() {
		int testCount = 0;
		Instant start = Instant.now();
		for (int i = 0; i < testCount; i++) {
			User user = userService.getUserById((long)i + 1);
			matchingFacadeService.getMyMatchingUsersWithCache(user);
		}
		Instant end = Instant.now();
		Duration duration = Duration.between(start, end);
		System.out.println(
			"[" + testCount + "times] With Cache Execution time: " + duration.toMillis() + " milliseconds");
	}

	@Test
	@DirtiesContext
	void matchingWithoutCacheTest() {
		int testCount = 0;
		Instant start = Instant.now();
		for (int i = 0; i < testCount; i++) {
			User user = userService.getUserById((long)i + 1);
			matchingFacadeService.getMyMatchingUsersWithoutCache(user);
		}
		Instant end = Instant.now();
		Duration duration = Duration.between(start, end);
		System.out.println(
			"[" + testCount + "times] Without Cache Execution time: " + duration.toMillis() + " milliseconds");
	}

	@Test
	@DirtiesContext
	void matchingWithCacheTest2() {
		int testCount = 0;
		Instant start = Instant.now();
		for (int i = 100; i < 100 + testCount; i++) {
			User user = userService.getUserById((long)i + 1);
			if (i % 5 == 0) {
				matchingFacadeService.deleteUserPersonalityForTest(user);
				PostUserPersonalityReqDTO postUserPersonalityReqDTO
					= new PostUserPersonalityReqDTO(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
				UserPersonality userPersonality = matchingFacadeService.saveUserPersonalityForTest(user,
					postUserPersonalityReqDTO);
				matchingFacadeService.addUserPersonality(userPersonality.getId());
			} else {
				matchingFacadeService.getMyMatchingUsersWithCache(user);
			}
		}
		Instant end = Instant.now();
		Duration duration = Duration.between(start, end);
		System.out.println(
			"[" + testCount + "times] With Cache Execution2 time: " + duration.toMillis() + " milliseconds");
	}

	@Test
	@DirtiesContext
	void matchingWithoutCacheTest2() {
		int testCount = 0;
		Instant start = Instant.now();
		for (int i = 10002; i < 10002 + testCount; i++) {
			User user = userService.getUserById((long)i + 1);
			if (true) {
				PostUserPersonalityReqDTO postUserPersonalityReqDTO
					= new PostUserPersonalityReqDTO(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
				matchingFacadeService.saveUserPersonalityForTest(user, postUserPersonalityReqDTO);
			} else {
				matchingFacadeService.getMyMatchingUsersWithoutCache(user);
			}
		}
		Instant end = Instant.now();
		Duration duration = Duration.between(start, end);
		System.out.println(
			"[" + testCount + "times] Without Cache Execution2 time: " + duration.toMillis() + " milliseconds");
	}

}