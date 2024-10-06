package com.example.tripmingle.adapter.out;

import com.example.tripmingle.common.constants.NotificationType;
import com.example.tripmingle.dto.etc.NotificationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.mail.MessagingException;

import static com.example.tripmingle.common.constants.NotificationType.EMAIL;

@Slf4j
@RequiredArgsConstructor
public class EmailNotificationAdapter implements Notification {
    private final EmailService emailService;

    @Override
    public boolean isAcceptable(NotificationType type) {
        return type==EMAIL;
    }

    @Override
    public void send(NotificationDTO notificationDTO) {
        String emailAddress = notificationDTO.getInformation();

        try {
            emailService.sendEmail(emailAddress);
            log.info("이메일이 성공적으로 전송되었습니다: " + emailAddress);
        } catch (MessagingException e) {
            log.info("이메일 전송 중 오류 발생: " + e.getMessage());
        }
    }
}
