package com.inspien.batch.application.port.in;


import com.inspien.batch.application.port.in.dto.OrderShipmentCommand;

public interface ShipmentCommandUseCase {
    void processShipment(OrderShipmentCommand command);
}
