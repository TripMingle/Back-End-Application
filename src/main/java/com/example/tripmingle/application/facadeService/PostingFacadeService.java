package com.example.tripmingle.application.facadeService;

import com.example.tripmingle.application.Service.PostingCommentService;
import com.example.tripmingle.application.Service.PostingService;
import com.example.tripmingle.port.in.PostingCommentUseCase;
import com.example.tripmingle.port.in.PostingUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostingFacadeService implements PostingUseCase, PostingCommentUseCase {

    private final PostingService postingService;
    private final PostingCommentService postingCommentService;

    @Override
    public void getRecentPostings() {
        postingService.getRecentPostings();
    }

    @Override
    public void getAllPostings() {
        postingService.getAllPostings();
    }

    @Override
    public void createPosting() {
        postingService.createPosting();
    }

    @Override
    public void updatePosting() {
        postingService.updatePosting();
    }

    @Override
    public void deletePosting() {
        postingService.deletePosting();
    }

    @Override
    public void getPostingInfo() {
        postingService.getPostingInfo();
        postingCommentService.getPostingCommentsByPostingId();
    }

    @Override
    public void createPostingComment() {
        postingCommentService.createPostingComment();
    }

    @Override
    public void updatePostingComment() {
        postingCommentService.updatePostingComment();
    }

    @Override
    public void deletePostingComment() {
        postingCommentService.deletePostingComment();
    }
}
