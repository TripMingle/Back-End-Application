package com.example.tripmingle.adapter.out;

import com.example.tripmingle.client.ElasticsearchClient;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.port.out.BoardSearchPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
        elasticSearchClient.createOrUpdateDocument(board.getId().toString(), document);
    }
}
