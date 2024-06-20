package com.example.tripmingle.application.facadeService;

import com.example.tripmingle.application.service.PostingCommentService;
import com.example.tripmingle.application.service.PostingLikesService;
import com.example.tripmingle.application.service.PostingService;
import com.example.tripmingle.application.service.UserService;
import com.example.tripmingle.dto.req.posting.*;
import com.example.tripmingle.dto.res.posting.*;
import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.entity.PostingComment;
import com.example.tripmingle.entity.PostingLikes;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.in.PostingCommentUseCase;
import com.example.tripmingle.port.in.PostingUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PostingFacadeService implements PostingUseCase, PostingCommentUseCase {

    private final PostingService postingService;
    private final PostingCommentService postingCommentService;
    private final PostingLikesService postingLikesService;
    private final UserService userService;

    @Transactional
    @Override
    public PostPostingResDTO createPosting(PostPostingReqDTO postPostingReqDTO) {
        Long postingId = postingService.createPosting(postPostingReqDTO);
        return PostPostingResDTO.builder()
                .postingId(postingId)
                .build();
    }

    @Transactional
    @Override
    public PatchPostingResDTO updatePosting(PatchPostingReqDTO patchPostingReqDTO) {
        Long postingId = postingService.updatePosting(patchPostingReqDTO);
        return PatchPostingResDTO.builder()
                .postingId(postingId)
                .build();
    }

    @Transactional
    @Override
    public DeletePostingResDTO deletePosting(Long postingId) {
        Posting deletedPosting = postingService.deletePosting(postingId);
        postingCommentService.deletePostingWithPostingComments(postingId);
        deletedPosting.deletePostingComments();
        postingLikesService.deletePostingWithPostingLikes(postingId);
        deletedPosting.deletePostingLikes();
        return DeletePostingResDTO.builder()
                .postingId(postingId)
                .build();
    }

    @Override
    public List<GetPostingsResDTO> getPreviewPostings(GetPreviewPostingReqDTO getPreviewPostingReqDTO) {
        List<Posting> postings = postingService.getPreviewPostings(getPreviewPostingReqDTO);
        return postings.stream()
                .map(posting -> GetPostingsResDTO.builder()
                        .postingId(posting.getId())
                        .title(posting.getTitle())
                        .content(posting.getContent())
                        .commentCount(posting.getCommentCount())
                        .likeCount(postingLikesService.getPostingTotalLikeCount(posting.getId()))
                        .country(posting.getCountry())
                        .userNickName(posting.getUser().getNickName())
                        .userAgeRange(posting.getUser().getAgeRange())
                        .userGender(posting.getUser().getGender())
                        .userNationality(posting.getUser().getNationality())
                        .myLikeState(postingLikesService.getPostingLikesState(posting))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public GetOnePostingResDTO getOnePosting(Long postingId) {
        Posting posting = postingService.getOnePosting(postingId);
        boolean postingLikesState = postingLikesService.getPostingLikesState(posting);
        List<PostingComment> postingComments = postingCommentService.getPostingComments(postingId);
        List<GetOnePostingCommentsResDTO> commentsInOnePosting = getCommentsInPosting(postingComments);
        return GetOnePostingResDTO.builder()
                .title(posting.getTitle())
                .content(posting.getContent())
                .country(posting.getCountry())
                .createAt(posting.getCreatedAt())
                .heartCount(0L)
                .postingComments(commentsInOnePosting)
                .userNickName(posting.getUser().getNickName())
                .userAgeRange(posting.getUser().getAgeRange())
                .userGender(posting.getUser().getGender())
                .userNationality(posting.getUser().getNationality())
                .selfIntroduce(posting.getUser().getSelfIntroduction())
                .userTemperature("36.5")
                .myLikeState(postingLikesState)
                .commentCount(posting.getCommentCount())
                .likeCount(postingLikesService.getPostingTotalLikeCount(posting.getId()))
                .build();
    }

    private List<GetOnePostingCommentsResDTO> getCommentsInPosting(List<PostingComment> postingComments) {
        return postingComments.stream().filter(filter -> filter.getPostingComment().isParentComment())
                .map(comments -> GetOnePostingCommentsResDTO.builder()
                        .commentId(comments.getId())
                        .userNickName(comments.getUser().getNickName())
                        .comment(comments.getComment())
                        .postingCoComment(postingComments.stream()
                                .filter(filter -> !filter.getPostingComment().isParentComment() && filter.getPostingComment().getId().equals(comments.getId()))
                                .map(cocomments -> GetOnePostingCoCommentResDTO.builder()
                                        .coCommentId(cocomments.getId())
                                        .parentCommentId(comments.getId())
                                        .userNickName(cocomments.getUser().getNickName())
                                        .coComment(cocomments.getComment())
                                        .build()).collect(Collectors.toList()))
                        .build()).toList();
    }

    @Override
    public List<GetPostingsResDTO> getAllPostings(GetAllPostingsReqDTO getAllPostingsReqDTO, Pageable pageable) {
        Page<Posting> getAllPostings = postingService.getAllPostings(getAllPostingsReqDTO, pageable);
        return getAllPostings.stream()
                .map(posting -> GetPostingsResDTO.builder()
                        .postingId(posting.getId())
                        .title(posting.getTitle())
                        .content(posting.getContent())
                        .country(posting.getCountry())
                        .userNickName(posting.getUser().getNickName())
                        .userAgeRange(posting.getUser().getAgeRange())
                        .userGender(posting.getUser().getGender())
                        .userNationality(posting.getUser().getNationality())
                        .myLikeState(postingLikesService.getPostingLikesState(posting))
                        .commentCount(posting.getCommentCount())
                        .likeCount(postingLikesService.getPostingTotalLikeCount(posting.getId()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<GetPostingsResDTO> getSearchPostings(String keyword, Pageable pageable) {
        Page<Posting> getSearchPostings = postingService.getSearchPostings(keyword, pageable);
        return getSearchPostings.stream()
                .filter(posting -> posting.getTitle().contains(keyword) || posting.getContent().contains(keyword))
                .map(posting -> GetPostingsResDTO.builder()
                        .postingId(posting.getId())
                        .title(posting.getTitle())
                        .content(posting.getContent())
                        .country(posting.getCountry())
                        .userNickName(posting.getUser().getNickName())
                        .userAgeRange(posting.getUser().getAgeRange())
                        .userGender(posting.getUser().getGender())
                        .userNationality(posting.getUser().getNationality())
                        .myLikeState(postingLikesService.getPostingLikesState(posting))
                        .commentCount(posting.getCommentCount())
                        .likeCount(postingLikesService.getPostingTotalLikeCount(posting.getId()))
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public PostPostingCommentResDTO createPostingComment(PostPostingCommentReqDTO postPostingCommentReqDTO) {
        Posting posting = postingService.getOnePosting(postPostingCommentReqDTO.getPostingId());
        Long newPostingCommentId = postingCommentService.createPostingComment(postPostingCommentReqDTO, posting);
        return PostPostingCommentResDTO.builder()
                .postingCommentId(newPostingCommentId)
                .build();
    }

    @Transactional
    @Override
    public PatchPostingCommentResDTO updatePostingComment(PatchPostingCommentReqDTO patchPostingCommentReqDTO) {
        Long postingComment = postingCommentService.updatePostingComment(patchPostingCommentReqDTO);
        return PatchPostingCommentResDTO.builder()
                .postingCommentId(postingComment)
                .build();
    }

    @Transactional
    @Override
    public DeletePostingCommentResDTO deletePostingComment(Long commentId) {
        Long postingCommentId = postingCommentService.deletePostingComment(commentId);
        return DeletePostingCommentResDTO.builder()
                .postingCommentId(postingCommentId)
                .build();
    }

    @Transactional
    @Override
    public PostingLikeToggleStateResDTO togglePostingLikes(Long postingId) {
        Posting posting = postingService.getOnePosting(postingId);
        boolean postingToggleState = postingLikesService.updatePostingLikesToggleState(posting);
        return PostingLikeToggleStateResDTO.builder()
                .postingId(postingId)
                .postingToggleState(postingToggleState)
                .build();
    }

    @Override
    public GetAllLikedPostingResDTO getMyLikedPostings(Pageable pageable) {
        Page<PostingLikes> getAllPostingLikes = postingLikesService.getAllPostingLikes(pageable);
        User user = userService.getCurrentUser();
        return GetAllLikedPostingResDTO.builder()
                .userNickName(user.getNickName())
                .userAgeRange(user.getAgeRange())
                .userGender(user.getGender())
                .userNationality(user.getNationality())
                .likedPostings(getLikedPostingsByCurrentUser(getAllPostingLikes))
                .build();
    }

    private List<GetLikedPostingResDTO> getLikedPostingsByCurrentUser(Page<PostingLikes> likedPostings) {
        return likedPostings.stream()
                .map(postingLikes -> GetLikedPostingResDTO.builder()
                        .postingId(postingLikes.getPosting().getId())
                        .title(postingLikes.getPosting().getTitle())
                        .content(postingLikes.getPosting().getContent())
                        .myLikeState(postingLikes.isToggleState())
                        .commentCount(postingLikes.getPosting().getCommentCount())
                        .likeCount(postingLikesService.getPostingTotalLikeCount(postingLikes.getPosting().getId()))
                        .country(postingLikes.getPosting().getCountry())
                        .build())
                .collect(Collectors.toList());
    }
}
