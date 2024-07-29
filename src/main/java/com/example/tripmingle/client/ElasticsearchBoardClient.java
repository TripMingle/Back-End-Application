package com.example.tripmingle.client;

import com.example.tripmingle.common.config.elasticsearch.ElasticsearchFeignConfig;
import com.example.tripmingle.entity.pojo.ElasticsearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "ElasticsearchClient", url = "${spring.elasticsearch.uris}", configuration = ElasticsearchFeignConfig.class)
public interface ElasticsearchBoardClient {

    @PostMapping(value = "${spring.elasticsearch.board-url}" + "/_doc/{id}", consumes = "application/json")
    Map<String, Object> createOrUpdateBoardDocument(@PathVariable("id") String id, @RequestBody Map<String, Object> document);

    @PostMapping(value = "${spring.elasticsearch.board-url}" + "/_search", consumes = "application/json")
    ElasticsearchResponse searchBoardDocument(@RequestBody Map<String, Object> query);

}
