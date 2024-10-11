package com.example.tripmingle.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.tripmingle.entity.pojo.ElasticsearchBoardResponse;

import feign.auth.BasicAuthRequestInterceptor;

@FeignClient(name = "ElasticsearchBoardClient", url = "${spring.elasticsearch.uris}", configuration = ElasticsearchBoardClient.FeignConfig.class)
public interface ElasticsearchBoardClient {

	@PostMapping(value = "${spring.elasticsearch.board-url}" + "/_doc/{id}", consumes = "application/json")
	Map<String, Object> createOrUpdateBoardDocument(@PathVariable("id") String id,
		@RequestBody Map<String, Object> document);

	@DeleteMapping(value = "${spring.elasticsearch.board-url}" + "/_doc/{id}")
	void deleteBoardDocument(@PathVariable("id") String id);

	@PostMapping(value = "${spring.elasticsearch.board-url}" + "/_search", consumes = "application/json")
	ElasticsearchBoardResponse searchBoardDocument(@RequestBody Map<String, Object> query);

	class FeignConfig {

		@Value("${spring.elasticsearch.username}")
		private String username;

		@Value("${spring.elasticsearch.password}")
		private String password;

		@Bean
		public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
			return new BasicAuthRequestInterceptor(username, password);
		}
	}

}
