package com.example.tripmingle.adapter.out;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.esentity.BoardEntity;
import com.example.tripmingle.port.out.BoardSearchPort;
import com.example.tripmingle.repository.ElasticsearchBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ElasticsearchBoardAdapter implements BoardSearchPort {
    private final ElasticsearchBoardRepository elasticsearchBoardRepository;

    @Override
    public Board saveBoard(Board board) {
        BoardEntity boardEntity = BoardEntity.builder()
                .id(board.getId().toString())
                .title(board.getTitle())
                .content(board.getContent())
                .build();
        elasticsearchBoardRepository.save(boardEntity);
        return board;
    }
}
