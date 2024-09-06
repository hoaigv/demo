package com.example.bookshop.infra.componet;

import com.example.bookshop.repository.InvalidatedTokenRepository;
import com.example.bookshop.service.IAsyncService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ScheduledTasks {
    InvalidatedTokenRepository tokenRepository;
    IAsyncService asyncService;
    @PostConstruct
    public void init() {
//        performTask();
//        performDailyTask();
    }


    @Scheduled(fixedRate = 6 * 60 * 60 * 1000)
    public void performTask() {
        tokenRepository.deleteAll();
    }
    @Scheduled(cron = "0 0 0 * * *")
    public void performDailyTask() {
        asyncService.processDataAsync();
    }
}
