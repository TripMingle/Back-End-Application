package com.example.tripmingle.repository;

import com.example.tripmingle.entity.esentity.BoardEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticsearchBoardRepository extends ElasticsearchRepository<BoardEntity, String> {
}
