package com.example.tripmingle.application.facadeService;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import com.example.tripmingle.dto.req.posting.PostPostingCommentReqDTO;
import com.example.tripmingle.dto.res.posting.GetOnePostingResDTO;
import com.example.tripmingle.port.in.PostingLockUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostingLockFacadeService implements PostingLockUseCase {

	private final PostingFacadeService postingFacadeService;
	private final RedissonClient redissonClient;
	private static final long LOCK_WAIT_TIME = 10L;   // 락 대기 시간 (초)
	private static final long LOCK_LEASE_TIME = 5L;   // 락 만료 시간 (초)

	public GetOnePostingResDTO createPostingComment(PostPostingCommentReqDTO postPostingCommentReqDTO) {
		String lockKey = "posting:" + postPostingCommentReqDTO.getPostingId();
		RLock lock = redissonClient.getLock(lockKey);

		boolean isLocked = false;
		try {
			isLocked = lock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);
			if (!isLocked) {
				log.warn("Lock 획득 실패: {}", lockKey);
				throw new RuntimeException("해당 게시물에 대한 락을 획득할 수 없습니다.");
			}

			// 비즈니스 로직 실행
			return postingFacadeService.createPostingComment(postPostingCommentReqDTO);

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // 인터럽트 상태 복원
			log.error("Lock 처리 중 인터럽트 발생: {}", e.getMessage(), e);
			throw new RuntimeException("Lock 처리 중 문제가 발생했습니다.", e);
		} finally {
			// 현재 스레드가 락을 보유한 경우에만 해제
			if (isLocked && lock.isHeldByCurrentThread()) {
				lock.unlock();
				log.info("Lock 해제 완료: {}", lockKey);
			}
		}
	}

}
