package com.example.tripmingle.application.facadeService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tripmingle.adapter.in.RedisMessageSubscriber;
import com.example.tripmingle.application.service.BoardBookMarkService;
import com.example.tripmingle.application.service.BoardLikesService;
import com.example.tripmingle.application.service.BoardService;
import com.example.tripmingle.application.service.CompanionService;
import com.example.tripmingle.application.service.MatchingService;
import com.example.tripmingle.application.service.UserPersonalityService;
import com.example.tripmingle.application.service.UserService;
import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.MatchingServerException;
import com.example.tripmingle.common.utils.CommonUtils;
import com.example.tripmingle.dto.req.matching.MatchingBoardReqDTO;
import com.example.tripmingle.dto.req.matching.PostUserPersonalityReqDTO;
import com.example.tripmingle.dto.res.matching.AddUserResDTO;
import com.example.tripmingle.dto.res.matching.MatchingBoardResDTO;
import com.example.tripmingle.dto.res.matching.MatchingUserResDTO;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.entity.UserPersonality;
import com.example.tripmingle.port.in.MatchingUseCase;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MatchingFacadeService implements MatchingUseCase {
	private final MatchingService matchingService;
	private final UserService userService;
	private final UserPersonalityService userPersonalityService;
	private final BoardService boardService;
	private final BoardLikesService boardLikesService;
	private final BoardBookMarkService boardBookMarkService;
	private final CompanionService companionService;
	private final CommonUtils commonUtils;

	@Override
	public Page<MatchingUserResDTO> getMyMatchingUsers(Pageable pageable) {
		User currentUser = userService.getCurrentUser();
		UserPersonality currentUserPersonality = userPersonalityService.getUserPersonalityByUserId(currentUser.getId());
		String messageId = UUID.randomUUID().toString();
		String response = "";
		try {
			CompletableFuture<String> future = matchingService.prepareData(currentUserPersonality, messageId);
			response = future.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (response.equals(RedisMessageSubscriber.FAIL_TO_RE_CALCULATE_USER_PERSONALITY)) {
			throw new MatchingServerException("matching server error", ErrorCode.MATCHING_SERVER_EXCEPTION);
		}

		List<Long> similarUsers = matchingService.getSimilarUserIds(currentUserPersonality.getId());

		int start = (int)pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), similarUsers.size());
		if (start > similarUsers.size()) {
			return new PageImpl<>(Collections.emptyList(), pageable,
				similarUsers.size()); // 요청한 페이지가 범위를 벗어나는 경우 빈 리스트 반환
		}
		similarUsers = similarUsers.subList(start, end);

		List<MatchingUserResDTO> matchingUserResDTOList = similarUsers.stream().map(userPersonalityId -> {
			UserPersonality userPersonality = userPersonalityService.getUserPersonalityById(userPersonalityId);
			return MatchingUserResDTO.builder()
				.userId(userPersonality.getUser().getId())
				.nickName(userPersonality.getUser().getNickName())
				.ageRange(userPersonality.getUser().getAgeRange())
				.gender(userPersonality.getUser().getGender())
				.nationality(userPersonality.getUser().getNationality())
				.selfIntroduction(userPersonality.getUser().getSelfIntroduction())
				.userPersonalityId(userPersonality.getId())
				.vegan(userPersonality.getVegan())
				.islam(userPersonality.getIslam())
				.hindu(userPersonality.getHindu())
				.smoking(userPersonality.getSmoking())
				.budget(userPersonality.getBudget())
				.accommodationFlexibility(userPersonality.getAccommodationFlexibility())
				.foodFlexibility(userPersonality.getFoodFlexibility())
				.activity(userPersonality.getActivity())
				.instagramPicture(userPersonality.getInstagramPicture())
				.foodExploration(userPersonality.getFoodExploration())
				.adventure(userPersonality.getAdventure())
				.personality(userPersonality.getPersonality())
				.schedule(userPersonality.getSchedule())
				.build();

		}).collect(Collectors.toList());

		return new PageImpl<>(matchingUserResDTOList, pageable, matchingUserResDTOList.size());
	}

	@Override
	@Transactional
	public UserPersonality saveUserPersonality(PostUserPersonalityReqDTO postUserPersonalityReqDTO) {
		User currentUser = userService.getCurrentUser();
		return userPersonalityService.saveUserPersonality(postUserPersonalityReqDTO, currentUser);
	}

	@Override
	@Transactional
	public void deleteUserPersonality() {
		User currentUser = userService.getCurrentUser();
		UserPersonality userPersonality = userPersonalityService.getUserPersonalityByUserId(currentUser.getId());
		userPersonalityService.delete(currentUser, userPersonality);
		String messageId = UUID.randomUUID().toString();
		String response = "";
		try {
			CompletableFuture<String> future = matchingService.deleteUserPersonality(userPersonality.getId(),
				messageId);
			response = future.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (response.equals(RedisMessageSubscriber.FAIL_TO_DELETE_USER_PERSONALITY)) {
			throw new MatchingServerException("matching server error", ErrorCode.MATCHING_SERVER_EXCEPTION);
		}
	}

	@Override
	public MatchingBoardResDTO matchingBoard(MatchingBoardReqDTO matchingBoardReqDTO) {
		User currentUser = userService.getCurrentUser();
		String messageId = UUID.randomUUID().toString();
		String response = "";
		try {
			CompletableFuture<String> future = matchingService.matchingBoard(messageId, currentUser.getId(),
				matchingBoardReqDTO);
			response = future.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (response.equals(RedisMessageSubscriber.FAIL_TO_ADD_USER_PERSONALITY)) {
			throw new MatchingServerException("matching server error", ErrorCode.MATCHING_SERVER_EXCEPTION);
		}

		Board board = boardService.getBoardById(Long.parseLong(response));

		return MatchingBoardResDTO.builder()
			.continent(board.getContinent())
			.countryName(board.getCountryName())
			.boardId(board.getId())
			.title(board.getTitle())
			.startDate(board.getStartDate())
			.endDate(board.getEndDate())
			.currentCount(board.getCurrentCount())
			.maxCount(board.getMaxCount())
			.language(board.getLanguage())
			.commentCount(board.getCommentCount())
			.likeCount(board.getLikeCount())
			.bookMarkCount(board.getBookMarkCount())
			.imageUrl(board.getImageUrl())
			.nickName(board.getUser().getNickName())
			.ageRange(board.getUser().getAgeRange())
			.gender(board.getUser().getGender())
			.nationality(board.getUser().getNationality())
			.isMine(currentUser.getId().equals(board.getUser().getId()))
			.isLiked(boardLikesService.isLikedBoard(currentUser, board))
			.isBookMarked(boardBookMarkService.isBookMarkedBoard(currentUser, board))
			.isParticipating(companionService.isParticipatingBoard(currentUser, board))
			.isExpired(commonUtils.isEndDatePassed(board.getEndDate()))
			.build();
	}

	@Override
	@Transactional
	public AddUserResDTO addUserPersonality(UserPersonality userPersonality) {
		String messageId = UUID.randomUUID().toString();
		String response = "";
		try {
			CompletableFuture<String> future = matchingService.addUser(userPersonality.getId(), messageId);
			response = future.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (response.equals(RedisMessageSubscriber.FAIL_TO_ADD_USER_PERSONALITY)) {
			throw new MatchingServerException("matching server error", ErrorCode.MATCHING_SERVER_EXCEPTION);
		}

		return AddUserResDTO.builder().resultMessage(response).build();
	}

}
