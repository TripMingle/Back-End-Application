package com.example.tripmingle.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.time.Instant;

import static com.example.tripmingle.common.constants.Constants.PAGE_SIZE;
import static com.example.tripmingle.common.constants.Constants.SORT_CREATING_CRITERIA;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("elasticsearch 사용")
    void searchBoard() {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE, Sort.by(Sort.Direction.DESC, SORT_CREATING_CRITERIA));

        Instant start = Instant.now();
        boardService.searchBoard("korea", "guide", pageable);
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println("Elasticsearch Duration = " + duration.toMillis());

    }

    @Test
    @DisplayName("Database 사용")
    void searchBoardByDB() {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE, Sort.by(Sort.Direction.DESC, SORT_CREATING_CRITERIA));

        Instant start = Instant.now();
        boardService.searchBoardByDB("korea", "guide", pageable);
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println("Database Duration = " + duration.toMillis());

    }
}