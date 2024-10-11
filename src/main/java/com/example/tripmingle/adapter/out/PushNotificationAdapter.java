package com.example.tripmingle.adapter.out;

import com.example.tripmingle.common.constants.NotificationType;
import com.example.tripmingle.dto.etc.NotificationDTO;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.example.tripmingle.common.constants.NotificationType.PUSH;

@RequiredArgsConstructor
@Slf4j
public class PushNotificationAdapter  implements Notification {
    private final FirebaseMessaging firebaseMessaging;

    @Override
    public boolean isAcceptable(NotificationType type) {
        return type==PUSH;
    }

    @Override
    public void send(NotificationDTO notificationDTO) {
        String token = notificationDTO.getInformation(); // FCM 토큰

        Message message = Message.builder()
                .setToken(token)
                .build();

        try {
            String response = firebaseMessaging.send(message);
            log.info("푸시 알림이 성공적으로 전송되었습니다. 응답: " + response);
        } catch (FirebaseMessagingException e) {
            log.info("푸시 알림 전송 중 오류 발생: " + e.getMessage());
        }
    }
}
