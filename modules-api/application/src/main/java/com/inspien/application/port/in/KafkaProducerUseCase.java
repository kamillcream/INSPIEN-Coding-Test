package com.inspien.application.port.in;

public interface KafkaProducerUseCase {
    void sendEvent(OrderShipmentPayload payload);
}
