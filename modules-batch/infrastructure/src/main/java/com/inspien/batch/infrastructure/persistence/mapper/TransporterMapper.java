package com.inspien.batch.infrastructure.persistence.mapper;

import com.inspien.batch.domain.Transporter;
import com.inspien.batch.infrastructure.persistence.entity.TransporterEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransporterMapper {
    Transporter toDomain(TransporterEntity entity);
    TransporterEntity toEntity(Transporter domain);
}
