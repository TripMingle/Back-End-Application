package com.example.tripmingle.application.service;

import com.example.tripmingle.dto.etc.NotificationDTO;
import com.example.tripmingle.port.out.NotificationPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationService {
    private final NotificationPort notificationPort;

    void sendNotification(){
        NotificationDTO notificationDTO = new NotificationDTO();

        notificationPort.send(notificationDTO);

    }

}
