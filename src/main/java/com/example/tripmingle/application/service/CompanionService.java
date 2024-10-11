package com.example.tripmingle.application.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.example.tripmingle.port.out.CacheManagerPort;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.LeaderCannotLeaveException;
import com.example.tripmingle.common.utils.UserUtils;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.Companion;
import com.example.tripmingle.entity.Position;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.CompanionPersistPort;

import lombok.RequiredArgsConstructor;

import static com.example.tripmingle.common.constants.Constants.COMPANION_LOCK_KEY;

@Service
@RequiredArgsConstructor
public class CompanionService {
	private final UserUtils userUtils;
	private final CompanionPersistPort companionPersistPort;
	private final CacheManagerPort cacheManagerPort;
	private final RedissonClient redissonClient;

	public void registerLeader(Board board, User user) {
		Companion companion = Companion.builder()
			.user(user)
			.board(board)
			.position(Position.LEADER)
			.build();
		companionPersistPort.saveCompanion(companion);
	}

	public void confirmUsers(Board board, User currentUser, List<User> userList) {
		userUtils.validateMasterUser(board.getUser().getId(), currentUser.getId());
		String lockKey = COMPANION_LOCK_KEY + board.getId();
		RLock lock = redissonClient.getLock(lockKey);

		try {
			if (lock.tryLock(5, 10, TimeUnit.SECONDS)) {
				try {
					long currentCompanionCount = cacheManagerPort.getCompanionCount(board.getId());

					if (currentCompanionCount + userList.size() > board.getMaxCount()) {
						throw new IllegalStateException("동행자 수가 최대값을 초과했습니다.");
					}

					userList.stream()
							.filter(user -> !user.getId().equals(board.getUser().getId()))
							.filter(user -> !companionPersistPort.existsByUserIdAndBoardId(user.getId(), board.getId()))
							.forEach(user -> {
								Companion companion = Companion.builder()
										.user(user)
										.board(board)
										.position(Position.MEMBER)
										.build();
								companionPersistPort.saveCompanion(companion);
							});

					cacheManagerPort.incrementCompanionCount(board.getId(), (long)userList.size());

				} finally {
					lock.unlock();
				}
			} else {
				throw new IllegalStateException("락 획득 실패");
			}
		} catch (InterruptedException e) {
			throw new IllegalStateException("락 획득 중 인터럽트 발생", e);
		}
	}

	public void leaveCompanion(Board board, User currentUser) {
		Companion companion = companionPersistPort.getCompanionByBoardAndUser(board, currentUser);
		if (companion.getPosition().equals(Position.LEADER)) {
			throw new LeaderCannotLeaveException("leader can't leave.", ErrorCode.LEADER_CANT_LEAVE);
		}
		companionPersistPort.deleteCompanion(companion);
	}

	public List<Companion> getCompanionsByBoardId(Long boardId) {
		return companionPersistPort.getCompanionsByBoardId(boardId);
	}

	public Page<Companion> getCompanionsByUser(User currentUser, Pageable pageable) {
		return companionPersistPort.getCompanionsByUserId(currentUser.getId(), pageable);
	}

	public boolean isParticipatingBoard(User currentUser, Board board) {
		return companionPersistPort.existsByUserIdAndBoardId(currentUser.getId(), board.getId());
	}

	public void deleteCompanionByBoardId(Long boardId) {
		companionPersistPort.deleteCompanionByBoardId(boardId);
	}
}
