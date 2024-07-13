package com.example.tripmingle.adapter.in;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.example.tripmingle.adapter.out.MessagePublisher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisMessageSubscriber implements MessageListener {
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private final MessagePublisher messagePublisher;

	//topic
	public static final String TOPIC_ADD_USER_RES_PUBLISH = "pubsub:addUserRes";
	public static final String TOPIC_RE_CALCULATE_USER_RES_PUBLISH = "pubsub:reCalculateUserRes";
	public static final String TOPIC_DELETE_USER_RES_PUBLISH = "pubsub:deleteUserRes";

	//message
	public static final String ADD_USER_PERSONALITY_SUCCESS = "add user personality success";
	public static final String FAIL_TO_ADD_USER_PERSONALITY = "fail to add user personality";
	public static final String RE_CALCULATE_USER_PERSONALITY_SUCCESS = "recalculate user personality success";
	public static final String FAIL_TO_RE_CALCULATE_USER_PERSONALITY = "fail to recalculate user personality";
	public static final String DELETE_USER_PERSONALITY_SUCCESS = "delete user personality success";
	public static final String FAIL_TO_DELETE_USER_PERSONALITY = "tail to delete user personality";
	public static final String MATCHING_SUCCESS = "matching success";
	public static final String FAIL_TO_MATCHING = "fail to matching";

	@PostConstruct
	private void init() {
		objectMapper.registerModule(new JavaTimeModule());
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		String channel = new String(message.getChannel());
		String messageBody = new String(message.getBody());
		StringBuilder cleanedMessageBody = new StringBuilder();
		char temp = 'a';
		for (char ch : messageBody.toCharArray()) {
			if (ch != '\\') {
				if (temp == '\"' && ch == '\"')
					continue;
				temp = ch;
				cleanedMessageBody.append(ch);
			}
		}
		messageBody = cleanedMessageBody.substring(1, cleanedMessageBody.length() - 1).toString();

		log.info("channel : " + channel + " message : " + messageBody);

		try {
			JsonNode jsonNode = objectMapper.readTree(messageBody);
			String result = jsonNode.get("message")
				.toString()
				.substring(1, jsonNode.get("message").toString().length() - 1);
			String messageId = jsonNode.get("messageId")
				.toString()
				.substring(1, jsonNode.get("messageId").toString().length() - 1);
			log.info(channel + " : " + result);

			if (result.equals(MATCHING_SUCCESS)) {
				String boardId = jsonNode.get("boardId")
					.toString();
				messagePublisher.completeMatchingResponse(messageId, result, boardId);
			} else {
				messagePublisher.completeResponse(messageId, result);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
