package com.example.tripmingle.adapter.out;

import com.example.tripmingle.client.ElasticsearchBoardClient;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.pojo.ElasticsearchResponse;
import com.example.tripmingle.port.out.BoardSearchPort;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ElasticsearchBoardAdapter implements BoardSearchPort {
    private final ElasticsearchBoardClient elasticSearchBoardClient;

    @Override
    public void saveBoard(Board board) {
        Map<String, Object> document = new HashMap<>();
        document.put("board_id", board.getId());
        document.put("title", board.getTitle());
        document.put("content", board.getContent());
        document.put("country_name", board.getCountryName());
        document.put("gender", board.getUser().getGender());
        document.put("language", board.getLanguage());
        elasticSearchBoardClient.createOrUpdateBoardDocument(board.getId().toString(), document);
    }

    @Async
    @Override
    public void updateBoard(Board board) {
        log.info("Starting Elasticsearch update for board ID: {}", board.getId());

        try {
            Map<String, Object> document = new HashMap<>();
            document.put("board_id", board.getId());
            document.put("title", board.getTitle());
            document.put("content", board.getContent());
            document.put("country_name", board.getCountryName());
            document.put("gender", board.getUser().getGender());
            document.put("language", board.getLanguage());
            elasticSearchBoardClient.createOrUpdateBoardDocument(board.getId().toString(), document);

            log.info("Completed Elasticsearch update for board ID: {}", board.getId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to update Elasticsearch document for board ID: " + board.getId(), e);
        }
    }

    @Async
    @Override
    public void deleteBoard(Board board) {
        elasticSearchBoardClient.deleteBoardDocument(board.getId().toString());
    }

    @Override
    public List<Long> searchBoard(String countryName, String keyword, Pageable pageable) {

        Map<String, Object> query = new HashMap<>();
        Map<String, Object> bool = new HashMap<>();
        List<Map<String, Object>> filterClauses = new ArrayList<>();
        List<Map<String, Object>> mustClauses = new ArrayList<>();
        List<Map<String, Object>> shouldClauses = new ArrayList<>();

        // country_name 매칭 (filter 절)
        Map<String, Object> countryTerm = new HashMap<>();
        Map<String, Object> countryField = new HashMap<>();
        countryField.put("country_name", countryName);
        countryTerm.put("term", countryField);
        filterClauses.add(countryTerm);

        // content 매칭 (should 절)
        Map<String, Object> contentMatch = new HashMap<>();
        Map<String, Object> contentField = new HashMap<>();
        contentField.put("content", keyword);
        contentMatch.put("match", contentField);
        shouldClauses.add(contentMatch);

        // title 매칭 (should 절)
        Map<String, Object> titleMatch = new HashMap<>();
        Map<String, Object> titleField = new HashMap<>();
        titleField.put("title", keyword);
        titleMatch.put("match", titleField);
        shouldClauses.add(titleMatch);

        Map<String, Object> shouldBool = new HashMap<>();
        shouldBool.put("should", shouldClauses);
        shouldBool.put("minimum_should_match", 1);

        mustClauses.add(Map.of("bool", shouldBool));
        bool.put("filter", filterClauses);
        bool.put("must", mustClauses);

        query.put("query", Map.of("bool", bool));
        query.put("from", pageable.getPageNumber() * pageable.getPageSize());
        query.put("size", pageable.getPageSize());

        log.info("Elasticsearch query: {}", query);

        ElasticsearchResponse response = elasticSearchBoardClient.searchBoardDocument(query);
        return response.getHits().getHits().stream()
                .map(hit -> hit.get_source().getBoard_id())
                .map(Long::parseLong)
                .collect(Collectors.toList());

    }
}
