package com.inspien.batch.application.service;

import com.inspien.batch.application.port.in.KafkaProducerUseCase;
import com.inspien.batch.application.port.in.ShipmentCommandUseCase;
import com.inspien.batch.application.port.in.dto.OrderShipmentCommand;
import com.inspien.batch.application.port.in.dto.ShipmentCreatedEvent;
import com.inspien.batch.application.port.out.ShipmentOutPort;
import com.inspien.batch.domain.Shipment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class ShipmentCommandService implements ShipmentCommandUseCase {
    private final ShipmentOutPort repo;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void processShipment(OrderShipmentCommand command) {
        Shipment shipment = Shipment.create(generateShipmentId(), command.getOrderId(), command.getItemId(),
                command.getApplicantKey(), command.getAddress());

        repo.save(shipment);

        eventPublisher.publishEvent(new ShipmentCreatedEvent(command.getOrderId()));
    }

    private String generateShipmentId() {
        String lastId = repo.findLastId();

        if (lastId == null) {
            return "A001";
        }

        char prefix = lastId.charAt(0);
        int number = Integer.parseInt(lastId.substring(1));

        number++;

        if (number > 999) {
            number = 1;
            prefix++;

            if (prefix > 'Z') {
                throw new IllegalStateException("배송 ID 최대 범위를 초과했습니다.");
            }
        }
        return String.format("%c%03d", prefix, number);
    }
}
