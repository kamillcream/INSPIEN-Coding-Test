package com.inspien.batch.infrastructure.kafka.consumer;

import com.inspien.batch.application.port.in.ShipmentCommandUseCase;
import com.inspien.batch.application.port.in.dto.OrderShipmentCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class ShipmentConsumer {
    private final ShipmentCommandUseCase shipmentCommandService;

    @KafkaListener(topics = "shipment.batch", groupId = "shipment-processor", containerFactory = "orderShipmentKafkaListenerContainerFactory")
    public void consumeBatchEvent(@Payload OrderShipmentCommand payload) {
        shipmentCommandService.processShipment(payload);
    }
}

