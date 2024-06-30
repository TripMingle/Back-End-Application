package com.example.tripmingle.adapter.in;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RedisMessageSubscriber implements MessageListener {

    private static final String ADD_USER_RES_PUBLISH = "pubsub:addUserRes";
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String messageBody = new String(message.getBody());
        System.out.println("@@@@@@@@" + messageBody + "channel : " + channel);
    }
}
