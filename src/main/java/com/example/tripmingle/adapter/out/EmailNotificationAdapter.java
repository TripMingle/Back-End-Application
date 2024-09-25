package com.example.tripmingle.adapter.out;

import com.example.tripmingle.application.service.NotificationService;
import com.example.tripmingle.port.out.NotificationPort;

public class EmailNotificationAdapter implements Noti{

    @Override
    public boolean isAcceptable(String type) {
        return type == "Email";
    }

    @Override
    public void send(NotificationPort.NotificationDTO notificationDTO) {
        //비즈니스로직..
    }
}
