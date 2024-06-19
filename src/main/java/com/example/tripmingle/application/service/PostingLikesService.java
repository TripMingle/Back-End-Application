package com.example.tripmingle.application.service;

import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.entity.PostingLikes;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.PostingLikesPersistPort;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
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

}
