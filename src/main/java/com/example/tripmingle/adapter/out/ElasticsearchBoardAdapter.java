package com.example.tripmingle.adapter.out;

import com.example.tripmingle.client.ElasticsearchClient;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.pojo.ElasticsearchResponse;
import com.example.tripmingle.port.out.BoardSearchPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ElasticsearchBoardAdapter implements BoardSearchPort {
    private final ElasticsearchClient elasticSearchClient;

    @Override
    public void saveBoard(Board board) {
        Map<String, Object> document = new HashMap<>();
        document.put("board_id", board.getId());
        document.put("title", board.getTitle());
        document.put("content", board.getContent());
        document.put("country_name", board.getCountryName());
        document.put("gender", board.getUser().getGender());
        document.put("language", board.getLanguage());
        elasticSearchClient.createOrUpdateBoardDocument(board.getId().toString(), document);
    }

    @Override
    public List<Long> searchBoard(String countryName, String keyword) {
        Map<String, Object> query = new HashMap<>();
        Map<String, Object> bool = new HashMap<>();
        List<Map<String, Object>> shouldClauses = new ArrayList<>();

        // country_name 매칭
        Map<String, Object> countryMatch = new HashMap<>();
        Map<String, Object> countryField = new HashMap<>();
        countryField.put("country_name", countryName);
        countryMatch.put("match", countryField);
        shouldClauses.add(countryMatch);

        // content 매칭
        Map<String, Object> contentMatch = new HashMap<>();
        Map<String, Object> contentField = new HashMap<>();
        contentField.put("content", keyword);
        contentMatch.put("match", contentField);
        shouldClauses.add(contentMatch);

        // title 매칭
        Map<String, Object> titleMatch = new HashMap<>();
        Map<String, Object> titleField = new HashMap<>();
        titleField.put("title", keyword);
        titleMatch.put("match", titleField);
        shouldClauses.add(titleMatch);

        Map<String, Object> should = new HashMap<>();
        should.put("should", shouldClauses);
        bool.put("bool", should);
        query.put("query", bool);

        ElasticsearchResponse response = elasticSearchClient.searchBoardDocument(query);
        return response.getHits().getHits().stream()
                .map(hit -> hit.get_source().getBoard_id())
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}
