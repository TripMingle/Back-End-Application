package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.PostingComment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PostingCommentPersistPort {

    List<PostingComment> getPostingCommentsByPostingId(Long postingId);

    List<PostingComment> getPostingCoCommentByParentCommentId(Long id);

    PostingComment getPostingCommentById(Long parentCommentId);

    PostingComment save(PostingComment postingComment);
}
