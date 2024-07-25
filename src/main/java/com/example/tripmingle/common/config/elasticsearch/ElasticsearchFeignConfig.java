package com.example.tripmingle.common.config.elasticsearch;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchFeignConfig {
    @Value("${spring.elasticsearch.username}")
    String username;

    @Value("${spring.elasticsearch.password}")
    String password;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(username, password);
    }
}
