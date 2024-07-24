package com.example.tripmingle.entity.esentity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;

@Builder
@Getter
@Document(indexName = "board-entity")
public class BoardEntity {

    @Id
    private String id;
    private String title;
    private String content;
}
