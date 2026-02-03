package com.inspien.api.infrastructure.kafka.consumer;

import com.inspien.api.application.port.out.OrderOutPort;
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
        log.info("[ORDER_STATUS_UPDATE] orderId={} step=SUCCESS", orderId);
    }
}
