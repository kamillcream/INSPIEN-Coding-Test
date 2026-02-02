package com.inspien.batch.bootstrap.processor;

import com.inspien.batch.domain.Transporter;
import com.inspien.domain.Order;
import com.inspien.infrastructure.mapper.OrderMapper;
import com.inspien.infrastructure.persistence.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderSendProcessor
        implements ItemProcessor<OrderEntity, Transporter> {

    private final OrderMapper orderMapper;

    @Override
    public Transporter process(OrderEntity entity) {
        Order order = orderMapper.toDomain(entity);

        return Transporter.builder()
                .shipmentId(order.getOrderId())
                .orderId(order.getOrderId())
                .itemId(order.getItemId())
                .applicantKey(order.getApplicantKey())
                .address(order.getAddress())
                .build();
    }
}