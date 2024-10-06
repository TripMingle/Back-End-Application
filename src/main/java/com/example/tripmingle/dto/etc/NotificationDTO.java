package com.example.tripmingle.dto.etc;

import com.example.tripmingle.common.constants.NotificationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDTO {
    String information;
    NotificationType type;
}
