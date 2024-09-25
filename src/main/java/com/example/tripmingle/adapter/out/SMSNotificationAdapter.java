package com.example.tripmingle.adapter.out;

import com.example.tripmingle.application.service.NotificationService;
import com.example.tripmingle.port.out.NotificationPort;

public class SMSNotificationAdapter implements Noti{

    @Override
    public boolean isAcceptable(String type) {
        return false;
    }

    @Override
    public void send(NotificationPort.NotificationDTO notificationDTO) {

    }
}
