package com.example.tripmingle.port.out;

import com.example.tripmingle.application.service.NotificationService;
import lombok.Getter;
import lombok.Setter;

public interface NotificationPort {
    void send(NotificationDTO notificationDTO);

    @Getter
    @Setter
    public class NotificationDTO{
        String information;
        String type;
    }

}
