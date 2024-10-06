package com.example.tripmingle.adapter.out;

import com.example.tripmingle.common.constants.NotificationType;
import com.example.tripmingle.dto.etc.NotificationDTO;

public interface Notification {
    public boolean isAcceptable(NotificationType type);
    public void send(NotificationDTO notificationDTO);
}
