package com.example.tripmingle.application.service;

import com.example.tripmingle.port.out.PostingPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class PostingService {
    private final PostingPersistPort postingPersistPort;

    public void getRecentPostings() {
        postingPersistPort.getRecentPostings();
    }

    public void getAllPostings() {
        postingPersistPort.getAllPostings();
    }

    public void createPosting() {
        postingPersistPort.createPosting();
    }

    public void updatePosting() {
        postingPersistPort.updatePosting();
    }

    public void deletePosting() {
        postingPersistPort.deletePostingById();
    }

    public void getPostingInfo() {
        postingPersistPort.getPostingById();
    }
}
