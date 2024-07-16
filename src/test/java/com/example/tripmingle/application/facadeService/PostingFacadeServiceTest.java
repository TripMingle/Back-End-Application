package com.example.tripmingle.application.facadeService;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.tripmingle.dto.req.posting.PostPostingCommentReqDTO;
import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.repository.PostingRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PostingFacadeServiceTest {

	@Autowired
	private PostingFacadeService postingFacadeService;
	@Autowired
	private PostingRepository postingRepository;

	@Test
	@DisplayName("비관적 락 적용")
	public void lockTest() throws InterruptedException {
		PostPostingCommentReqDTO reqDTO = new PostPostingCommentReqDTO();
		reqDTO.setPostingId(18L);
		reqDTO.setComment("Hello World Test");
		reqDTO.setParentCommentId(-1L);

		final int threadCount = 50;
		final ExecutorService executorService = Executors.newFixedThreadPool(50);
		final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

		// when
		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					postingFacadeService.createPostingComment(reqDTO);
				} finally {
					countDownLatch.countDown();
				}
			});
		}
		countDownLatch.await();

		// then
		Posting posting = postingRepository.findById(18L).get();
		assertThat(posting.getCommentCount()).isEqualTo(51);
	}
}
