package com.inspien.batch.infrastructure.kafka.service;

import com.inspien.batch.application.port.in.KafkaProducerUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService implements KafkaProducerUseCase {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendEvent(String orderId) {
        kafkaTemplate.send("shipment.success", orderId)
                .whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("Failed to send shipment success event for orderId: {}", orderId, ex);
                    } else {
                    log.info("Shipment success event sent for orderId: {}", orderId);
                    }
                });
    }
}
