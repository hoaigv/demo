package com.example.bookshop.service.impl;

import com.example.bookshop.dto.notification.PushNotificationRequest;
import com.example.bookshop.dto.notification.PushNotificationResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PushNotificationService {
    FCMService fcmService;
    public void sendPushNotificationToToken(PushNotificationRequest request) {
        try {
        fcmService.sendMessageToToken(request);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
