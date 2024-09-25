package com.example.tripmingle.adapter.out;

import com.example.tripmingle.port.out.NotificationPort;

public interface Noti {
    public boolean isAcceptable(String type);
    public void send(NotificationPort.NotificationDTO notificationDTO);
}
