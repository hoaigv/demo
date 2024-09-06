package com.example.bookshop.service.impl;

import com.example.bookshop.dto.notification.PushNotificationRequest;
import com.example.bookshop.exception.CustomRunTimeException;
import com.example.bookshop.exception.ErrorCode;
import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class FCMService {
    public void sendMessageToToken(PushNotificationRequest request) {
       try{

           Message message = getPreconfiguredMessageToToken(request);
           FirebaseMessaging.getInstance().sendAsync(message).get();
       } catch (ExecutionException | InterruptedException e) {
           throw new CustomRunTimeException(ErrorCode.NOTIFICATION_NOT_SUCCESS);
       }

    }


    private Message getPreconfiguredMessageToToken (PushNotificationRequest request) {
        return getPreconfiguredMessageBuilder(request).setToken(request.getToken()).build();
    }
    private Message getPreconfiguredMessageWithoutData(PushNotificationRequest request){
        return getPreconfiguredMessageBuilder(request).setTopic(request.getTopic()).build();
    }
    private Message getPreconfiguredMessageWithData(Map<String,String> data, PushNotificationRequest request){
        return getPreconfiguredMessageBuilder(request).putAllData(data).setToken(request.getToken()).build();
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setTag(topic).build()).build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
        Notification.Builder notification = Notification.builder()
                .setTitle(request.getTitle())
                .setBody(request.getMessage());

        return Message.builder()
                .setAndroidConfig(androidConfig)
                .setApnsConfig(apnsConfig)
                .setNotification(notification.build());
    }

}