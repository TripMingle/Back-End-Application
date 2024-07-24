package com.example.tripmingle.repository;

import com.example.tripmingle.entity.esentity.BoardEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ESRepository extends ElasticsearchRepository<BoardEntity, String> {
}
