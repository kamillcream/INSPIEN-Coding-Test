package com.inspien.api.application.port.in;

public interface KafkaProducerUseCase {
    void sendEvent(OrderShipmentPayload payload);
}
