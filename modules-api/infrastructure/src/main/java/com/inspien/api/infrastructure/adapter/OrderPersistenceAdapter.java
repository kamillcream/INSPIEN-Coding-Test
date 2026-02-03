package com.inspien.api.infrastructure.adapter;

import com.inspien.api.application.port.out.OrderOutPort;
import com.inspien.api.domain.Order;
import com.inspien.api.infrastructure.mapper.OrderMapper;
import com.inspien.api.infrastructure.persistence.entity.OrderEntity;
import com.inspien.api.infrastructure.persistence.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements OrderOutPort {
    private final OrderJpaRepository repo;
    private final OrderMapper mapper;

    @Override
    public void save(Order order) {
        OrderEntity entity = mapper.toEntity(order);
        repo.save(entity);
    }

    @Override
    public String findLastId() {
        Pageable lastOne = PageRequest.of(0, 1);
        List<OrderEntity> results = repo.findLatestOrder(lastOne);

        return results.isEmpty() ? null : results.getFirst().getOrderId();
    }

    @Override
    public void markOrderSuccess(String orderId) {
        repo.markAsY(orderId);
    }

    @Override
    public List<Order> findShipmentCandidates() {
        List<OrderEntity> entities = repo.findPendingOrders();
        if(entities.isEmpty()) {
            return List.of();
        }
        return entities.stream().map(mapper::toDomain).toList();
    }
}
