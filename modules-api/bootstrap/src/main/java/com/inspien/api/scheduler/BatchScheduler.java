package com.inspien.api.scheduler;

import com.inspien.application.port.in.KafkaProducerUseCase;
import com.inspien.application.port.in.OrderShipmentPayload;
import com.inspien.application.port.out.OrderOutPort;
import com.inspien.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class BatchScheduler {
    private final OrderOutPort orderRepo;
    private final KafkaProducerUseCase kafkaProducerService;

    // TODO: 5분으로 변경
    @Scheduled(cron = "*/5 * * * * *")
    public void publishShipmentReadyEvents() {
        List<Order> orders = orderRepo.findShipmentCandidates();
        List<OrderShipmentPayload> payloads = convertToPayload(orders);
        payloads.forEach(kafkaProducerService::sendEvent);
    }

    private List<OrderShipmentPayload> convertToPayload(List<Order> entities) {
        if(entities.isEmpty()) {
            return List.of();
        }
        return entities.stream()
                .map(domain -> OrderShipmentPayload.create(domain.getOrderId(), domain.getItemId(),
                        domain.getApplicantKey(), domain.getAddress()))
                .toList();
    }
}