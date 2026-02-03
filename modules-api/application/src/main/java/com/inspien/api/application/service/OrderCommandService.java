package com.inspien.api.application.service;

import com.inspien.api.application.port.in.OrderCommandUseCase;
import com.inspien.api.application.port.in.OrderCreateCommand;
import com.inspien.api.application.port.in.OrderHeader;
import com.inspien.api.application.port.in.OrderItem;
import com.inspien.api.application.port.out.OrderOutPort;
import com.inspien.api.domain.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
@Slf4j
public class OrderCommandService implements OrderCommandUseCase {
    private final OrderOutPort repo;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${applicantKey}")
    private String applicantKey;

    @Override
    public void processOrder(OrderCreateCommand command) {
        List<OrderHeader> headers = command.getHeaders();
        Map<String, OrderHeader> headersByUser = groupingHeaderByUser(headers);

        for (OrderItem item : command.getItems()) {
            if (!headersByUser.containsKey(item.getUserId())) {
                log.error(
                        "[ORDER_SAVE] step=VALIDATION_FAIL userId={} reason=HEADER_NOT_FOUND",
                        item.getUserId()
                );
                continue;
            }

            OrderHeader header = headersByUser.get(item.getUserId());

            Order order = Order.create(generateOrderId(), item.getUserId(), item.getItemId(), applicantKey,
                    header.getName(), header.getAddress(), item.getItemName(), String.valueOf(item.getPrice()), header.getStatus());

            repo.save(order);
            log.info("[ORDER_SAVE] orderId={} step=SUCCESS", order.getOrderId());

            eventPublisher.publishEvent(order);
        }
    }

    private Map<String, OrderHeader> groupingHeaderByUser(List<OrderHeader> headers) {
        return headers.stream()
                .collect(Collectors.toMap(
                        OrderHeader::getUserId,
                                Function.identity(),
                        (existing, replacement) -> existing
                ));
    }

    private String generateOrderId() {
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
                log.error(
                        "[ORDER_SAVE] step=ID_GENERATE_FAIL lastId={} prefix={} reason=ID_RANGE_OVER",
                        lastId,
                        prefix
                );
                throw new IllegalStateException("주문 ID 최대 범위를 초과했습니다.");
            }
        }
        return String.format("%c%03d", prefix, number);
    }
}
