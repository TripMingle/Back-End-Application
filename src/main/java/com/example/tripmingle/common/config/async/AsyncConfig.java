package com.example.tripmingle.common.config.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsyncExecutor-");
        executor.initialize();
        return executor;
    }
    @Bean
    public DelegatingSecurityContextAsyncTaskExecutor taskExecutor2(ThreadPoolTaskExecutor delegate) {
        return new DelegatingSecurityContextAsyncTaskExecutor(delegate);
    }

}
