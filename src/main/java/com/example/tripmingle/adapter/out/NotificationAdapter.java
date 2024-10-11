package com.example.tripmingle.adapter.out;

import com.example.tripmingle.dto.etc.NotificationDTO;
import com.example.tripmingle.port.out.NotificationPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class NotificationAdapter implements NotificationPort {
    private final List<Notification> notificationList;

    @Override
    public void send(NotificationDTO notificationDTO) {
        notificationList.stream()
                .filter(notification -> notification.isAcceptable(notificationDTO.getType()))
                .forEach(notification -> notification.send(notificationDTO));
    }

}
