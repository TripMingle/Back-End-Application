package com.example.tripmingle.client;

import com.example.tripmingle.entity.pojo.ElasticsearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "ElasticsearchClient", url = "${spring.elasticsearch.uris}")
public interface ElasticsearchClient {

    @PostMapping(value = "${spring.elasticsearch.board-url}" + "/_doc/{id}", consumes = "application/json")
    Map<String, Object> createOrUpdateDocument(@PathVariable("id") String id, @RequestBody Map<String, Object> document);

    @PostMapping(value = "${spring.elasticsearch.board-url}" + "/board/_search", consumes = "application/json")
    ElasticsearchResponse searchDocument(@RequestBody Map<String, Object> query);


}
