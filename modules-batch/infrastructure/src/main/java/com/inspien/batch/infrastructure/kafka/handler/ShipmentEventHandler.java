package com.inspien.batch.infrastructure.kafka.handler;

import com.inspien.batch.application.port.in.KafkaProducerUseCase;
import com.inspien.batch.application.port.in.dto.ShipmentCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ShipmentEventHandler {
    private final KafkaProducerUseCase kafkaProducerService;
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleShipmentCreated(ShipmentCreatedEvent event) {
        kafkaProducerService.sendEvent(event.orderId());
    }
}

