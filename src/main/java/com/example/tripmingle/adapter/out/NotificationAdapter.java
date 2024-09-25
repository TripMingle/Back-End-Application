package com.example.tripmingle.adapter.out;

import com.example.tripmingle.port.out.NotificationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@RequiredArgsConstructor
public class NotificationAdapter implements NotificationPort {
    private final List<Noti> notiList;

    @Override
    public void send(NotificationDTO notificationDTO) {
        notiList.forEach(i -> {
            if(i.isAcceptable(notificationDTO.getType())){
                i.send(notificationDTO);
            }
        });
    }

}
