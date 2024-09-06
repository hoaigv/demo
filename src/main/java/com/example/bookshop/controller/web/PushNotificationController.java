package com.example.bookshop.controller.web;

import com.example.bookshop.dto.ApiResponse;
import com.example.bookshop.dto.notification.PushNotificationRequest;
import com.example.bookshop.service.impl.PushNotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PushNotificationController {
     PushNotificationService pushNotificationService;
    @PostMapping("/notification/token")
    public ApiResponse<Void> sendTokenNotification(@RequestBody PushNotificationRequest request) {
        pushNotificationService.sendPushNotificationToToken(request);
        return ApiResponse.<Void>builder().build();
    }
}
