package com.example.tripmingle.application.service;

import com.example.tripmingle.port.out.NotificationPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationService {
    private final NotificationPort notificationPort;

    void sendNotification(){
        NotificationPort.NotificationDTO notificationDTO = new NotificationPort.NotificationDTO();

        notificationPort.send(notificationDTO);

    }

}
