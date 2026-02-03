package com.inspien.api.scheduler;

import com.inspien.api.application.port.in.SchedulerUseCase;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class BatchScheduler {
    private final SchedulerUseCase schedulerService;

    @Scheduled(cron = "0 */5 * * * *", zone = "Asia/Seoul")
    public void publishShipmentReadyEvents() {
        schedulerService.publishShipmentReadyEvents();
    }
}