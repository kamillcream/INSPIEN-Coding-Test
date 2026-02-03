package com.inspien.batch.application.port.out;

import com.inspien.batch.domain.Shipment;

public interface ShipmentOutPort {
    void save(Shipment shipment);
    String findLastId();
}
