package com.example.tripmingle.common.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.example.tripmingle.adapter.in.RedisMessageSubscriber;

@Configuration
public class RedisConfig {

	private final String host;
	private final int port;

	public RedisConfig(@Value("${spring.data.redis.host}") String host, @Value("${spring.data.redis.port}") int port) {
		this.host = host;
		this.port = port;
	}

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(host, port);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		return redisTemplate;
	}

	@Bean
	public RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory,
		MessageListenerAdapter messageListener) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);  // RedisConnectionFactory를 설정합니다.

		container.addMessageListener(messageListener, addUserResTopic());
		container.addMessageListener(messageListener, reCalculateUserResTopic());
		container.addMessageListener(messageListener, deleteUserResTopic());

		return container;
	}

	@Bean
	public MessageListenerAdapter messageListener(RedisMessageSubscriber redisMessageSubscriber) {
		return new MessageListenerAdapter(redisMessageSubscriber);  // RedisMessageSubscriber를 메시지 리스너로 설정합니다.
	}

	@Bean
	public ChannelTopic addUserResTopic() {
		return new ChannelTopic("pubsub:addUserRes");
	}

	@Bean
	public ChannelTopic reCalculateUserResTopic() {
		return new ChannelTopic("pubsub:reCalculateUserRes");
	}

	@Bean
	public ChannelTopic deleteUserResTopic() {
		return new ChannelTopic("pubsub:deleteUserRes");
	}

}


