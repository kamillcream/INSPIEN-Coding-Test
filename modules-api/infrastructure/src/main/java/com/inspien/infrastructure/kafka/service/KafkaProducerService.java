package com.inspien.infrastructure.kafka.service;

import com.inspien.application.port.in.KafkaProducerUseCase;
import com.inspien.application.port.in.OrderShipmentPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService implements KafkaProducerUseCase {

    private final KafkaTemplate<String, OrderShipmentPayload> kafkaTemplate;

    public void sendEvent(OrderShipmentPayload payload) {
        kafkaTemplate.send("shipment.batch", payload);
    }
}
