package com.inspien.api.infrastructure.kafka.service;

import com.inspien.api.application.port.in.KafkaProducerUseCase;
import com.inspien.api.application.port.in.OrderShipmentPayload;
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
        kafkaTemplate.send("shipment.batch", payload)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error(
                                "[SHIPMENT_BATCH] step=SEND_FAIL orderId={} topic=shipment.batch reason={} message={}",
                                payload.getOrderId(),
                                ex.getClass().getSimpleName(),
                                ex.getMessage(),
                                ex
                        );
                    } else {
                        log.info(
                                "[SHIPMENT_BATCH] step=SEND_SUCCESS orderId={} topic={} partition={} offset={}",
                                payload.getOrderId(),
                                result.getRecordMetadata().topic(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset()
                        );
                    }
                });

    }
}
