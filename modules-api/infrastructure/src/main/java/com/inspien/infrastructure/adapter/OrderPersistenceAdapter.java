package com.inspien.infrastructure.adapter;

import com.inspien.application.port.out.OrderOutPort;
import com.inspien.domain.Order;
import com.inspien.infrastructure.mapper.OrderMapper;
import com.inspien.infrastructure.persistence.entity.OrderEntity;
import com.inspien.infrastructure.persistence.repository.OrderJpaRepository;
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
        List<OrderEntity> results = repo.findAllByOrderByOrderIdDesc(lastOne);

        return results.isEmpty() ? null : results.getFirst().getOrderId();
    }
}
