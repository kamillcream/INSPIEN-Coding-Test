package com.inspien.infrastructure.kafka.consumer;

import com.inspien.application.port.out.OrderOutPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
@Slf4j
public class SuccessEventConsumer {
    private final OrderOutPort repo;

    @KafkaListener(topics = "shipment.success", groupId = "order-processor", containerFactory = "eventKafkaListenerContainerFactory")
    @Transactional
    public void consumeSuccessEvent(@Payload String orderId) {
        repo.markOrderSuccess(orderId);
    }
}
