package com.example.tripmingle.application.facadeService;

import static com.example.tripmingle.common.constants.Constants.*;

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
import com.example.tripmingle.application.service.ContinentService;
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
import com.example.tripmingle.dto.res.board.GetBoardInfoResDTO;
import com.example.tripmingle.dto.res.board.GetBoardsResDTO;
import com.example.tripmingle.dto.res.board.GetCompanionsResDTO;
import com.example.tripmingle.dto.res.board.ParentBoardCommentResDTO;
import com.example.tripmingle.dto.res.board.SearchBoardResDTO;
import com.example.tripmingle.dto.res.board.ToggleStateResDTO;
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
	private final ContinentService continentService;

	@Override
	public List<GetBoardsResDTO> getRecentBoards(String countryName) {
		List<Board> boardList = boardService.getRecentBoardsByCountryName(countryName);
		User currentUser = userService.getCurrentUser();
		return boardList.stream().map(board -> makeBoardDTO(board, currentUser))
			.collect(Collectors.toList());
	}

	@Override
	public Page<GetBoardsResDTO> getBoardList(GetAllBoardReqDTO getAllBoardReqDTO, Pageable pageable) {
		Page<Board> boardPage = boardService.getAllBoards(getAllBoardReqDTO, pageable);
		User currentUser = userService.getCurrentUser();
		return boardPage.map(board -> makeBoardDTO(board, currentUser));
	}

	@Override
	public GetBoardInfoResDTO getBoard(Long boardId) {
		Board board = boardService.getBoardById(boardId);
		User currentUser = userService.getCurrentUser();
		List<ParentBoardCommentResDTO> boardComments
			= structureBoardComment(boardCommentService.getBoardCommentsByBoardId(board.getId()), currentUser);
		return makeBoardInfoDTO(board, currentUser, boardComments);
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
			.userImageUrl(
				boardComment.getUser().getUserImageUrl() == null || boardComment.getUser().getUserImageUrl().isEmpty()
					? "" : boardComment.getUser().getUserImageUrl())
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
			.userImageUrl(
				boardComment.getUser().getUserImageUrl() == null || boardComment.getUser().getUserImageUrl().isEmpty()
					? "" : boardComment.getUser().getUserImageUrl())
			.isMine(currentUser.getId().equals(boardComment.getUser().getId()))
			.build();
	}

	@Override
	@Transactional
	public GetBoardInfoResDTO createBoard(CreateBoardReqDTO createBoardReqDTO) {
		User currentUser = userService.getCurrentUser();
		String randomImageUrl = countryImageService.getRandomCountryImage(createBoardReqDTO.getCountryName());
		if (randomImageUrl.isEmpty()) {
			randomImageUrl = continentService.getContinentByContinentName(createBoardReqDTO.getContinent())
				.getImageUrl();
		}
		Board board = boardService.createBoard(createBoardReqDTO, currentUser, randomImageUrl);
		companionService.registerLeader(board, currentUser);
		boardScheduleService.createBoardSchedule(board, currentUser, createBoardReqDTO.getCreateBoardSchedule());

		return makeBoardInfoDTO(board, currentUser, new ArrayList<>());

	}

	@Override
	@Transactional
	public GetBoardInfoResDTO updateBoard(Long boardId, UpdateBoardReqDTO patchBoardReqDTO) {
		User currentUser = userService.getCurrentUser();
		Board board = boardService.updateBoard(boardId, patchBoardReqDTO, currentUser);
		List<ParentBoardCommentResDTO> boardComments
			= structureBoardComment(boardCommentService.getBoardCommentsByBoardId(board.getId()), currentUser);
		return makeBoardInfoDTO(board, currentUser, boardComments);
	}

	@Override
	@Transactional
	public void deleteBoard(Long boardId) {
		User currentUser = userService.getCurrentUser();
		companionService.deleteCompanionByBoardId(boardId);
		boardScheduleService.deleteBoardScheduleByBoardId(boardId);
		boardCommentService.deleteBoardCommentByBoardId(boardId);
		boardLikesService.deleteBoardLikesByBoardId(boardId);
		boardBookMarkService.deleteBoardBookMarksByBoardId(boardId);
		boardService.deleteBoard(boardId, currentUser);
	}

	@Override
	public Page<SearchBoardResDTO> searchBoard(String countryName, String keyword, Pageable pageable) {
		Page<Board> boardPage = boardService.searchBoard(countryName, keyword, pageable);

		return boardPage.map(board -> SearchBoardResDTO.builder()
			.continent(board.getContinent())
			.countryName(board.getCountryName())
			.boardId(board.getId())
			.title(board.getTitle())
			.createdAt(board.getCreatedAt())
			.startDate(board.getStartDate())
			.endDate(board.getEndDate())
			.currentCount(board.getCurrentCount())
			.maxCount(board.getMaxCount())
			.language(board.getLanguage())
			.commentCount(board.getCommentCount())
			.likeCount(board.getLikeCount())
			.bookMarkCount(board.getBookMarkCount())
			.content(board.getContent())
			.imageUrl(board.getImageUrl())
			.nickName(board.getUser().getNickName())
			.ageRange(board.getUser().getAgeRange())
			.gender(board.getUser().getGender())
			.nationality(board.getUser().getNationality())
			.userImageUrl(board.getUser().getUserImageUrl() == null || board.getUser().getUserImageUrl().isEmpty()
				? "" : board.getUser().getUserImageUrl())
			.build());
	}

	@Override
	@Transactional
	public void createBoardComment(CreateBoardCommentReqDTO createBoardCommentReqDTO) {
		User currentUser = userService.getCurrentUser();
		Board board = boardService.getBoardById(createBoardCommentReqDTO.getBoardId());
		BoardComment boardComment = boardCommentService.createBoardComment(createBoardCommentReqDTO, board,
			currentUser);
		Long parentBoardCommentId =
			boardComment.isParentBoardCommentNull() ? NO_PARENT_COMMENT_ID :
				boardComment.getParentBoardComment().getId();

		if (!parentBoardCommentId.equals(NO_PARENT_COMMENT_ID) &&
			!createBoardCommentReqDTO.getBoardId()
				.equals(boardCommentService.getBoardByCommentId(parentBoardCommentId).getId())) {
			throw new IllegalArgumentException("board id not equal");
		}
	}

	@Override
	@Transactional
	public void deleteBoardComment(Long commentId) {
		User currentUser = userService.getCurrentUser();
		Board board = boardCommentService.getBoardByCommentId(commentId);
		int commentCount = boardCommentService.deleteBoardComment(commentId, currentUser);
		boardService.updateCommentCount(board, commentCount);

	}

	@Override
	@Transactional
	public void updateBoardComment(UpdateBoardCommentReqDTO updateBoardCommentReqDTO, Long commentId) {
		User currentUser = userService.getCurrentUser();
		boardCommentService.updateBoardComment(updateBoardCommentReqDTO, commentId, currentUser);
	}

	@Override
	public void getBoardsWithinRange() {
		boardService.getBoardsWithinRange();
	}

	@Override
	@Transactional
	public ToggleStateResDTO toggleBoardBookMark(Long boardId) {
		User currentUser = userService.getCurrentUser();
		Board board = boardService.getBoardById(boardId);
		return ToggleStateResDTO.builder()
			.boardId(board.getId())
			.state(boardBookMarkService.toggleBoardBookMark(board, currentUser))
			.build();
	}

	@Override
	public Page<GetBoardsResDTO> getMyBookMarkedBoards(Pageable pageable) {
		User currentUser = userService.getCurrentUser();
		Page<BoardBookMark> boardBookMarks = boardBookMarkService.getMyBookMarkedBoards(currentUser, pageable);
		return boardBookMarks.map(boardBookmark -> makeBoardDTO(boardBookmark.getBoard(), currentUser));
	}

	@Override
	@Transactional
	public ToggleStateResDTO toggleBoardLikes(Long boardId) {
		User currentUser = userService.getCurrentUser();
		Board board = boardService.getBoardById(boardId);
		return ToggleStateResDTO.builder()
			.boardId(board.getId())
			.state(boardLikesService.toggleBoardLikes(board, currentUser))
			.build();
	}

	@Override
	public Page<GetBoardsResDTO> getMyLikedBoards(Pageable pageable) {
		User currentUser = userService.getCurrentUser();
		Page<BoardLikes> boardLikes = boardLikesService.getMyLikedBoards(currentUser, pageable);
		return boardLikes.map(boardLike -> makeBoardDTO(boardLike.getBoard(), currentUser));
	}

	@Override
	public Page<GetBoardsResDTO> getMyBoards(Pageable pageable) {
		User currentUser = userService.getCurrentUser();
		Page<Board> boardList = boardService.getBoardsByUser(currentUser, pageable);
		return boardList.map(board -> makeBoardDTO(board, currentUser));
	}

	@Override
	@Transactional
	public void confirmUsers(Long boardId, ConfirmUsersReqDTO confirmUsersReqDTO) {
		Board board = boardService.getBoardById(boardId);
		User currentUser = userService.getCurrentUser();
		List<User> users = userService.getUsersById(confirmUsersReqDTO.getUserIds());
		companionService.confirmUsers(board, currentUser, users);
	}

	@Override
	@Transactional
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
		return companionList.map(companion -> makeBoardDTO(companion.getBoard(), currentUser));
	}

	@Override
	public void getClusteredBoards() {
		boardService.getBoardsByIdList();
	}

	private GetBoardInfoResDTO makeBoardInfoDTO(Board board, User currentUser,
		List<ParentBoardCommentResDTO> boardComments) {
		return GetBoardInfoResDTO.builder()
			.continent(board.getContinent())
			.countryName(board.getCountryName())
			.boardId(board.getId())
			.title(board.getTitle())
			.content(board.getContent())
			.language(board.getLanguage())
			.tripType(commonUtils.convertStringToArray(board.getTripType()))
			.preferGender(commonUtils.convertDoubleToInt(board.getPreferGender()))
			.preferSmoking(commonUtils.convertDoubleToInt(board.getPreferSmoking()))
			.preferBudget(commonUtils.convertDoubleToInt(board.getPreferBudget()))
			.preferPhoto(commonUtils.convertDoubleToInt(board.getPreferPhoto()))
			.preferDrink(commonUtils.convertDoubleToInt(board.getPreferDrink()))
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
			.boardComments(boardComments)
			.userId(board.getUser().getId())
			.nickName(board.getUser().getNickName())
			.ageRange(board.getUser().getAgeRange())
			.gender(board.getUser().getGender())
			.nationality(board.getUser().getNationality())
			.selfIntroduction(
				board.getUser().getSelfIntroduction() == null || board.getUser().getSelfIntroduction().isEmpty()
					? "" : board.getUser().getSelfIntroduction())
			.userImageUrl(board.getUser().getUserImageUrl() == null || board.getUser().getUserImageUrl().isEmpty()
				? "" : board.getUser().getUserImageUrl())
			.userScore(board.getUser().getUserScore())
			.imageUrl(board.getImageUrl())
			.build();
	}

	private GetBoardsResDTO makeBoardDTO(Board board, User currentUser) {
		return GetBoardsResDTO.builder()
			.continent(board.getContinent())
			.countryName(board.getCountryName())
			.boardId(board.getId())
			.title(board.getTitle())
			.createdAt(board.getCreatedAt())
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
			.userImageUrl(board.getUser().getUserImageUrl() == null || board.getUser().getUserImageUrl().isEmpty()
				? "" : board.getUser().getUserImageUrl())
			.isMine(currentUser.getId().equals(board.getUser().getId()))
			.isLiked(boardLikesService.isLikedBoard(currentUser, board))
			.isBookMarked(boardBookMarkService.isBookMarkedBoard(currentUser, board))
			.isParticipating(companionService.isParticipatingBoard(currentUser, board))
			.isExpired(commonUtils.isEndDatePassed(board.getEndDate()))
			.build();
	}
}
