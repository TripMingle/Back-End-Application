package com.example.tripmingle.application.facadeService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tripmingle.application.service.BoardBookMarkService;
import com.example.tripmingle.application.service.BoardCommentService;
import com.example.tripmingle.application.service.BoardLikesService;
import com.example.tripmingle.application.service.BoardScheduleService;
import com.example.tripmingle.application.service.BoardService;
import com.example.tripmingle.application.service.CompanionService;
import com.example.tripmingle.application.service.CountryImageService;
import com.example.tripmingle.application.service.UserService;
import com.example.tripmingle.common.utils.CommonUtils;
import com.example.tripmingle.dto.etc.ChildBoardCommentDTO;
import com.example.tripmingle.dto.req.board.ConfirmUsersReqDTO;
import com.example.tripmingle.dto.req.board.CreateBoardCommentReqDTO;
import com.example.tripmingle.dto.req.board.CreateBoardReqDTO;
import com.example.tripmingle.dto.req.board.GetAllBoardReqDTO;
import com.example.tripmingle.dto.req.board.UpdateBoardCommentReqDTO;
import com.example.tripmingle.dto.req.board.UpdateBoardReqDTO;
import com.example.tripmingle.dto.res.board.CreateBoardCommentResDTO;
import com.example.tripmingle.dto.res.board.GetBoardInfoResDTO;
import com.example.tripmingle.dto.res.board.GetBoardsResDTO;
import com.example.tripmingle.dto.res.board.GetCompanionsResDTO;
import com.example.tripmingle.dto.res.board.ParentBoardCommentResDTO;
import com.example.tripmingle.dto.res.board.PostBoardResDTO;
import com.example.tripmingle.dto.res.board.ToggleStateResDTO;
import com.example.tripmingle.dto.res.board.UpdateBoardCommentResDTO;
import com.example.tripmingle.dto.res.board.UpdateBoardResDTO;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BoardBookMark;
import com.example.tripmingle.entity.BoardComment;
import com.example.tripmingle.entity.BoardLikes;
import com.example.tripmingle.entity.Companion;
import com.example.tripmingle.entity.Position;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.in.BoardCommentUseCase;
import com.example.tripmingle.port.in.BoardUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardFacadeService implements BoardUseCase, BoardCommentUseCase {
	private final BoardService boardService;
	private final BoardCommentService boardCommentService;
	private final UserService userService;
	private final CommonUtils commonUtils;
	private final BoardBookMarkService boardBookMarkService;
	private final BoardLikesService boardLikesService;
	private final CompanionService companionService;
	private final BoardScheduleService boardScheduleService;
	private final CountryImageService countryImageService;

	@Override
	public List<GetBoardsResDTO> getRecentBoards(String countryName) {
		List<Board> boardList = boardService.getRecentBoardsByCountryName(countryName);
		User currentUser = userService.getCurrentUser();

		return boardList
			.stream().map(board -> GetBoardsResDTO.builder()
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
				.build())
			.collect(Collectors.toList());
	}

	@Override
	public Page<GetBoardsResDTO> getAllBoards(GetAllBoardReqDTO getAllBoardReqDTO, Pageable pageable) {
		Page<Board> boardPage = boardService.getAllBoards(getAllBoardReqDTO, pageable);
		User currentUser = userService.getCurrentUser();

		return boardPage.map(board -> GetBoardsResDTO.builder()
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
			.build());
	}

	@Override
	public GetBoardInfoResDTO getBoard(Long boardId) {
		Board board = boardService.getBoardById(boardId);
		User currentUser = userService.getCurrentUser();
		List<ParentBoardCommentResDTO> boardComments
			= structureBoardComment(boardCommentService.getBoardCommentsByBoardId(board.getId()), currentUser);

		return GetBoardInfoResDTO.builder()
			.continent(board.getContinent())
			.countryName(board.getCountryName())
			.boardId(board.getId())
			.title(board.getTitle())
			.content(board.getContent())
			.language(board.getLanguage())
			//.personalType(commonUtils.convertStringToArray(board.getPersonalType()))
			//.tripType(commonUtils.convertStringToArray(board.getTripType()))
			.preferGender(board.getPreferGender())
			.preferSmoking(board.getPreferSmoking())
			.preferActivity(board.getPreferActivity())
			.preferInstagramPicture(board.getPreferInstagramPicture())
			.preferFoodExploration(board.getPreferFoodExploration())
			.preferAdventure(board.getPreferAdventure())
			.startDate(board.getStartDate())
			.endDate(board.getEndDate())
			.currentCount(board.getCurrentCount())
			.maxCount(board.getMaxCount())
			.createdAt(board.getCreatedAt())
			.commentCount(board.getCommentCount())
			.likeCount(board.getLikeCount())
			.bookMarkCount(board.getBookMarkCount())
			.isMine(currentUser.getId().equals(board.getUser().getId()))
			.isLiked(boardLikesService.isLikedBoard(currentUser, board))
			.isBookMarked(boardBookMarkService.isBookMarkedBoard(currentUser, board))
			.isParticipating(companionService.isParticipatingBoard(currentUser, board))
			.isExpired(commonUtils.isEndDatePassed(board.getEndDate()))
			.boardCommentResDTOS(boardComments)
			.userId(board.getUser().getId())
			.nickName(board.getUser().getNickName())
			.ageRange(board.getUser().getAgeRange())
			.gender(board.getUser().getGender())
			.nationality(board.getUser().getNationality())
			.selfIntroduction(board.getUser().getSelfIntroduction())
			.imageUrl(board.getImageUrl())
			.build();
	}

	private List<ParentBoardCommentResDTO> structureBoardComment(List<BoardComment> boardComments, User currentUser) {
		Map<Long, List<BoardComment>> commentMap = new HashMap<>();
		List<BoardComment> parentList = new ArrayList<>();

		boardComments.forEach(comment -> {
			if (comment.isParentBoardCommentNull()) {
				commentMap.put(comment.getId(), new ArrayList<>());
				parentList.add(comment);
			} else {
				Long parentId = comment.getParentBoardComment().getId();
				commentMap.get(parentId).add(comment);
			}
		});

		return parentList.stream()
			.map(parent -> {
				Long parentId = parent.getId();
				ParentBoardCommentResDTO parentDTO = getParentBoardCommentInfo(parent, currentUser);
				List<ChildBoardCommentDTO> childDTOs = commentMap.getOrDefault(parentId, Collections.emptyList())
					.stream()
					.map(child -> getChildBoardCommentInfo(child, parentId, currentUser))
					.collect(Collectors.toList());
				parentDTO.setChildBoards(childDTOs);
				return parentDTO;
			})
			.collect(Collectors.toList());
	}

	private ParentBoardCommentResDTO getParentBoardCommentInfo(BoardComment boardComment, User currentUser) {

		return ParentBoardCommentResDTO.builder()
			.boardId(boardComment.getBoard().getId())
			.boardCommentId(boardComment.getId())
			.content(boardComment.getContent())
			.registeredDate(boardComment.getCreatedAt())
			.userId(boardComment.getUser().getId())
			.userNickname(boardComment.getUser().getNickName())
			.isMine(currentUser.getId().equals(boardComment.getUser().getId()))
			.build();
	}

	private ChildBoardCommentDTO getChildBoardCommentInfo(BoardComment boardComment, Long parentId, User currentUser) {

		return ChildBoardCommentDTO.builder()
			.boardId(boardComment.getBoard().getId())
			.boardCommentId(boardComment.getId())
			.parentId(parentId)
			.content(boardComment.getContent())
			.registeredDate(boardComment.getCreatedAt())
			.userId(boardComment.getUser().getId())
			.userNickname(boardComment.getUser().getNickName())
			.isMine(currentUser.getId().equals(boardComment.getUser().getId()))
			.build();
	}

	@Override
	@Transactional(readOnly = false)
	public PostBoardResDTO createBoard(CreateBoardReqDTO createBoardReqDTO) {
		User currentUser = userService.getCurrentUser();
		String randomImageUrl = countryImageService.getRandomCountryImage(createBoardReqDTO.getCountryName());
		Board board = boardService.createBoard(createBoardReqDTO, currentUser, randomImageUrl);
		companionService.registerLeader(board, currentUser);
		boardScheduleService.createBoardSchedule(board, currentUser, createBoardReqDTO.getCreateBoardScheduleReqDTOS());
		return PostBoardResDTO.builder().boardId(board.getId()).build();
	}

	@Override
	@Transactional(readOnly = false)
	public UpdateBoardResDTO updateBoard(Long boardId, UpdateBoardReqDTO patchBoardReqDTO) {
		User currentUser = userService.getCurrentUser();
		Board board = boardService.updateBoard(boardId, patchBoardReqDTO, currentUser);
		return UpdateBoardResDTO.builder().boardId(board.getId())
			.build();
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteBoard(Long boardId) {
		User currentUser = userService.getCurrentUser();
		boardScheduleService.deleteBoardScheduleByBoardId(boardId);
		boardCommentService.deleteBoardCommentByBoardId(boardId);
		boardLikesService.deleteBoardLikesByBoardId(boardId);
		boardBookMarkService.deleteBoardBookMarksByBoardId(boardId);
		boardService.deleteBoard(boardId, currentUser);
	}

	@Override
	public Page<GetBoardsResDTO> searchBoard(String countryName, String keyword, Pageable pageable) {
		Page<Board> boardPage = boardService.searchBoard(countryName, keyword, pageable);
		User currentUser = userService.getCurrentUser();
		return boardPage.map(board -> GetBoardsResDTO.builder()
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
			.build());
	}

	@Override
	@Transactional(readOnly = false)
	public CreateBoardCommentResDTO createBoardComment(CreateBoardCommentReqDTO createBoardCommentReqDTO) {
		User currentUser = userService.getCurrentUser();
		Board board = boardService.getBoardById(createBoardCommentReqDTO.getBoardId());
		BoardComment boardComment = boardCommentService.createBoardComment(createBoardCommentReqDTO, board,
			currentUser);
		Long parentBoardCommentId =
			boardComment.isParentBoardCommentNull() ? -1 : boardComment.getParentBoardComment().getId();

		return CreateBoardCommentResDTO.builder()
			.parentBoardCommentId(parentBoardCommentId)
			.BoardCommentId(boardComment.getId())
			.content(boardComment.getContent())
			.userId(boardComment.getUser().getId())
			.userNickname(boardComment.getUser().getNickName())
			.createdAt(boardComment.getCreatedAt())
			.build();
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteBoardComment(Long commentId) {
		User currentUser = userService.getCurrentUser();
		Board board = boardCommentService.getBoardByCommentId(commentId);
		int commentCount = boardCommentService.deleteBoardComment(commentId, currentUser);
		boardService.updateCommentCount(board, commentCount);

	}

	@Override
	@Transactional(readOnly = false)
	public UpdateBoardCommentResDTO updateBoardComment(UpdateBoardCommentReqDTO updateBoardCommentReqDTO,
		Long commentId) {
		User currentUser = userService.getCurrentUser();
		BoardComment boardComment = boardCommentService.updateBoardComment(updateBoardCommentReqDTO, commentId,
			currentUser);
		return UpdateBoardCommentResDTO.builder().boardCommentId(boardComment.getId()).build();
	}

	@Override
	public void getBoardsWithinRange() {
		boardService.getBoardsWithinRange();
	}

	@Override
	@Transactional(readOnly = false)
	public ToggleStateResDTO toggleBoardBookMark(Long boardId) {
		User currentUser = userService.getCurrentUser();
		Board board = boardService.getBoardById(boardId);
		boolean state = boardBookMarkService.toggleBoardBookMark(board, currentUser);
		return ToggleStateResDTO.builder()
			.state(state)
			.build();
	}

	@Override
	public Page<GetBoardsResDTO> getMyBookMarkedBoards(Pageable pageable) {
		User currentUser = userService.getCurrentUser();
		Page<BoardBookMark> boardBookMarks = boardBookMarkService.getMyBookMarkedBoards(currentUser, pageable);

		return boardBookMarks.map(boardBookmark -> GetBoardsResDTO.builder()
			.continent(boardBookmark.getBoard().getContinent())
			.countryName(boardBookmark.getBoard().getCountryName())
			.boardId(boardBookmark.getBoard().getId())
			.title(boardBookmark.getBoard().getTitle())
			.startDate(boardBookmark.getBoard().getStartDate())
			.endDate(boardBookmark.getBoard().getEndDate())
			.currentCount(boardBookmark.getBoard().getCurrentCount())
			.maxCount(boardBookmark.getBoard().getMaxCount())
			.language(boardBookmark.getBoard().getLanguage())
			.commentCount(boardBookmark.getBoard().getCommentCount())
			.likeCount(boardBookmark.getBoard().getLikeCount())
			.bookMarkCount(boardBookmark.getBoard().getBookMarkCount())
			.imageUrl(boardBookmark.getBoard().getImageUrl())
			.nickName(boardBookmark.getBoard().getUser().getNickName())
			.ageRange(boardBookmark.getBoard().getUser().getAgeRange())
			.gender(boardBookmark.getBoard().getUser().getGender())
			.nationality(boardBookmark.getBoard().getUser().getNationality())
			.isMine(currentUser.getId().equals(boardBookmark.getBoard().getUser().getId()))
			.isLiked(boardLikesService.isLikedBoard(currentUser, boardBookmark.getBoard()))
			.isBookMarked(boardBookMarkService.isBookMarkedBoard(currentUser, boardBookmark.getBoard()))
			.isParticipating(companionService.isParticipatingBoard(currentUser, boardBookmark.getBoard()))
			.isExpired(commonUtils.isEndDatePassed(boardBookmark.getBoard().getEndDate()))
			.build());
	}

	@Override
	@Transactional(readOnly = false)
	public ToggleStateResDTO toggleBoardLikes(Long boardId) {
		User currentUser = userService.getCurrentUser();
		Board board = boardService.getBoardById(boardId);
		boolean state = boardLikesService.toggleBoardLikes(board, currentUser);
		return ToggleStateResDTO.builder()
			.state(state)
			.build();
	}

	@Override
	public Page<GetBoardsResDTO> getMyLikedBoards(Pageable pageable) {
		User currentUser = userService.getCurrentUser();
		Page<BoardLikes> boardLikes = boardLikesService.getMyLikedBoards(currentUser, pageable);

		return boardLikes.map(boardLike -> GetBoardsResDTO.builder()
			.continent(boardLike.getBoard().getContinent())
			.countryName(boardLike.getBoard().getCountryName())
			.boardId(boardLike.getBoard().getId())
			.title(boardLike.getBoard().getTitle())
			.startDate(boardLike.getBoard().getStartDate())
			.endDate(boardLike.getBoard().getEndDate())
			.currentCount(boardLike.getBoard().getCurrentCount())
			.maxCount(boardLike.getBoard().getMaxCount())
			.language(boardLike.getBoard().getLanguage())
			.commentCount(boardLike.getBoard().getCommentCount())
			.likeCount(boardLike.getBoard().getLikeCount())
			.bookMarkCount(boardLike.getBoard().getBookMarkCount())
			.imageUrl(boardLike.getBoard().getImageUrl())
			.nickName(boardLike.getBoard().getUser().getNickName())
			.ageRange(boardLike.getBoard().getUser().getAgeRange())
			.gender(boardLike.getBoard().getUser().getGender())
			.nationality(boardLike.getBoard().getUser().getNationality())
			.isMine(currentUser.getId().equals(boardLike.getBoard().getUser().getId()))
			.isLiked(boardLikesService.isLikedBoard(currentUser, boardLike.getBoard()))
			.isBookMarked(boardBookMarkService.isBookMarkedBoard(currentUser, boardLike.getBoard()))
			.isParticipating(companionService.isParticipatingBoard(currentUser, boardLike.getBoard()))
			.isExpired(commonUtils.isEndDatePassed(boardLike.getBoard().getEndDate()))
			.build());
	}

	@Override
	public Page<GetBoardsResDTO> getMyBoards(Pageable pageable) {
		User currentUser = userService.getCurrentUser();
		Page<Board> boardList = boardService.getBoardsByUser(currentUser, pageable);
		return boardList.map(board -> GetBoardsResDTO.builder()
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
			.build());
	}

	@Override
	@Transactional(readOnly = false)
	public void confirmUsers(Long boardId, ConfirmUsersReqDTO confirmUsersReqDTO) {
		Board board = boardService.getBoardById(boardId);
		User currentUser = userService.getCurrentUser();
		List<User> users = userService.getUsersById(confirmUsersReqDTO.getUserIds());
		companionService.confirmUsers(board, currentUser, users);
	}

	@Override
	@Transactional(readOnly = false)
	public void leaveCompanion(Long boardId) {
		Board board = boardService.getBoardById(boardId);
		User currentUser = userService.getCurrentUser();
		companionService.leaveCompanion(board, currentUser);
	}

	@Override
	public List<GetCompanionsResDTO> getCompanions(Long boardId) {
		Board board = boardService.getBoardById(boardId);
		List<Companion> companions = companionService.getCompanionsByBoardId(board.getId());

		return companions.stream().map(companion -> GetCompanionsResDTO.builder()
			.userId(companion.getUser().getId())
			.nickName(companion.getUser().getNickName())
			.ageRange(companion.getUser().getAgeRange())
			.gender(companion.getUser().getGender())
			.nationality(companion.getUser().getNationality())
			.selfIntroduction(companion.getUser().getSelfIntroduction())
			.isLeader(companion.getPosition().equals(Position.LEADER))
			.build()).collect(Collectors.toList());
	}

	@Override
	public Page<GetBoardsResDTO> getMyCompanionBoards(Pageable pageable) {
		User currentUser = userService.getCurrentUser();
		Page<Companion> companionList = companionService.getCompanionsByUser(currentUser, pageable);
		return companionList.map(companion -> GetBoardsResDTO.builder()
			.continent(companion.getBoard().getContinent())
			.countryName(companion.getBoard().getCountryName())
			.boardId(companion.getBoard().getId())
			.title(companion.getBoard().getTitle())
			.startDate(companion.getBoard().getStartDate())
			.endDate(companion.getBoard().getEndDate())
			.currentCount(companion.getBoard().getCurrentCount())
			.maxCount(companion.getBoard().getMaxCount())
			.language(companion.getBoard().getLanguage())
			.commentCount(companion.getBoard().getCommentCount())
			.likeCount(companion.getBoard().getLikeCount())
			.bookMarkCount(companion.getBoard().getBookMarkCount())
			.imageUrl(companion.getBoard().getImageUrl())
			.nickName(companion.getUser().getNickName())
			.ageRange(companion.getUser().getAgeRange())
			.gender(companion.getUser().getGender())
			.nationality(companion.getUser().getNationality())
			.isMine(currentUser.getId().equals(companion.getUser().getId()))
			.isLiked(boardLikesService.isLikedBoard(currentUser, companion.getBoard()))
			.isBookMarked(boardBookMarkService.isBookMarkedBoard(currentUser, companion.getBoard()))
			.isParticipating(companionService.isParticipatingBoard(currentUser, companion.getBoard()))
			.isExpired(commonUtils.isEndDatePassed(companion.getBoard().getEndDate()))
			.build());
	}

	@Override
	public void getClusteredBoards() {
		boardService.getBoardsByIdList();
	}
}
