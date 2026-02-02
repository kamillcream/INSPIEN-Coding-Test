package com.inspien.batch.infrastructure.persistence.adapter;

import com.inspien.batch.application.port.out.ShipmentOutPort;
import com.inspien.batch.domain.Shipment;
import com.inspien.batch.infrastructure.persistence.entity.ShipmentEntity;
import com.inspien.batch.infrastructure.persistence.mapper.ShipmentMapper;
import com.inspien.batch.infrastructure.persistence.repository.ShipmentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ShipmentPersistenceAdapter implements ShipmentOutPort {
    private final ShipmentJpaRepository repo;
    private final ShipmentMapper mapper;

    @Override
    public void save(Shipment shipment) {
        ShipmentEntity entity = mapper.toEntity(shipment);
        repo.save(entity);
    }

    @Override
    public String findLastId() {
        Pageable lastOne = PageRequest.of(0, 1);
        List<ShipmentEntity> results = repo.findLatestShipment(lastOne);

        return results.isEmpty() ? null : results.getFirst().getShipmentId();
    }
}
