package com.example.tripmingle.application.service;

import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.entity.PostingLikes;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.PostingLikesPersistPort;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostingLikesService {

    private final PostingLikesPersistPort postingLikesPersistPort;
    private final UserPersistPort userPersistPort;

    public boolean updatePostingLikesToggleState(Posting posting) {
        User currentUser = userPersistPort.findCurrentUserByEmail();
        PostingLikes postingLikes = null;
        if (!postingLikesPersistPort.existsByPostingIdAndUserId(posting.getId(), currentUser.getId())) {
            postingLikes = PostingLikes.builder()
                    .posting(posting)
                    .user(currentUser)
                    .build();
            postingLikesPersistPort.save(postingLikes);
            return true;
        }
        postingLikes = postingLikesPersistPort.findByPostingIdAndUserId(posting.getId(), currentUser.getId());
        postingLikes.updatePostingLikeToggleState();
        return postingLikes.isToggleState();
    }

    public Page<PostingLikes> getAllPostingLikes(Pageable pageable) {
        User user = userPersistPort.findCurrentUserByEmail();
        Page<PostingLikes> postingLikes = postingLikesPersistPort.getAllPostingLikes(user.getId(), pageable);
        return postingLikes;
    }

    public boolean getPostingLikesState(Posting posting) {
        User user = userPersistPort.findCurrentUserByEmail();
        PostingLikes myPostingLike = postingLikesPersistPort.findByPostingIdAndUserId(posting.getId(), user.getId());
        if (myPostingLike == null) {
            return false;
        }
        return myPostingLike.isToggleState();
    }
}
