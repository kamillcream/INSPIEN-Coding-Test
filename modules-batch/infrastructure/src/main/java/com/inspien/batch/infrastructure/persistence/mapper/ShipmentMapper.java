package com.inspien.batch.infrastructure.persistence.mapper;

import com.inspien.batch.domain.Shipment;
import com.inspien.batch.infrastructure.persistence.entity.ShipmentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {
    Shipment toDomain(ShipmentEntity entity);
    ShipmentEntity toEntity(Shipment domain);
}
