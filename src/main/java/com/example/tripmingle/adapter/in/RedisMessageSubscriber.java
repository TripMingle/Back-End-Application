package com.example.tripmingle.adapter.in;

import com.example.tripmingle.application.facadeService.MatchingFacadeService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisMessageSubscriber implements MessageListener {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final MatchingFacadeService matchingFacadeService;

    private static final String ADD_USER_RES_PUBLISH = "pubsub:addUserRes";
    private static final String FAIL_TO_ADD_USER_PERSONALITY = "fail to add user personality";
    private static final String ADD_USER_PERSONALITY_SUCCESS = "add user personality success";

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String messageBody = new String(message.getBody());
        StringBuilder cleanedMessageBody = new StringBuilder();
        char temp = 'a';
        for (char ch : messageBody.toCharArray()) {
            if (ch != '\\') {
                if(temp=='\"' && ch=='\"') continue;
                temp = ch;
                cleanedMessageBody.append(ch);
            }
        }
        messageBody = cleanedMessageBody.substring(1, cleanedMessageBody.length()-1).toString();

        try {
            JsonNode jsonNode = objectMapper.readTree(messageBody);
            String result = jsonNode.get("message").toString().substring(1, jsonNode.get("message").toString().length()-1);

            System.out.println(channel + " : " + result);
            
        }catch (Exception e) {
            //여기 어떻게?
            e.printStackTrace();
        }
        
    }
}
