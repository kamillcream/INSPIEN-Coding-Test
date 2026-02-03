package com.inspien.application.service;

import com.inspien.application.port.in.KafkaProducerUseCase;
import com.inspien.application.port.in.OrderShipmentPayload;
import com.inspien.application.port.in.SchedulerUseCase;
import com.inspien.application.port.out.OrderOutPort;
import com.inspien.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SchedulerService implements SchedulerUseCase {
    private final OrderOutPort orderRepo;
    private final KafkaProducerUseCase kafkaProducerService;

    @Override
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
