package com.example.bookshop.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FCMInitializer {
    @Value("${firebase.service-account-key}")
    String firebaseConfigPath;

    @PostConstruct
    public void init() {
        try  (InputStream serviceAccount = new ClassPathResource(firebaseConfigPath).getInputStream()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("FirebaseApp initialized");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }

}
