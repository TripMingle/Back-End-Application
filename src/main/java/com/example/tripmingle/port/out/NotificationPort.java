package com.example.tripmingle.port.out;

import com.example.tripmingle.dto.etc.NotificationDTO;


public interface NotificationPort {
    void send(NotificationDTO notificationDTO);

}
