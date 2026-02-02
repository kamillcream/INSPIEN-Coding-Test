package com.inspien.batch.application.port.in;

public interface KafkaProducerUseCase {
    void sendEvent(String orderId);
}
