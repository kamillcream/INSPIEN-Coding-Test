package com.inspien.api.infrastructure.mapper;

import com.inspien.api.domain.Order;
import com.inspien.api.infrastructure.persistence.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toDomain(OrderEntity entity);
    OrderEntity toEntity(Order domain);
}
