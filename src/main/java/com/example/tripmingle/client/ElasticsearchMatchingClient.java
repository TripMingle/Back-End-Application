package com.example.tripmingle.client;

import com.example.tripmingle.entity.pojo.ElasticsearchMatchingResponse;
import com.example.tripmingle.entity.pojo.ElasticsearchMatchingResponse2;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "ElasticsearchMatchingClient", url = "${spring.elasticsearch.uris}", configuration = ElasticsearchBoardClient.FeignConfig.class)
public interface ElasticsearchMatchingClient {

    @PostMapping(value = "${spring.elasticsearch.matching-url}" + "/_search", consumes = "application/json")
    ElasticsearchMatchingResponse getSimilarUsers(Map<String, Object> query);

    @PostMapping(value = "${spring.elasticsearch.matching-url-es}" + "/_search", consumes = "application/json")
    ElasticsearchMatchingResponse2 getSimilarUsersBYES(Map<String, Object> query);

    @PostMapping(value = "${spring.elasticsearch.matching-url-es}" + "/_doc/{id}", consumes = "application/json")
    void createOrUpdateUserPersonality(@PathVariable("id") Long userPersonalityId, Map<String, Object> document);
}
