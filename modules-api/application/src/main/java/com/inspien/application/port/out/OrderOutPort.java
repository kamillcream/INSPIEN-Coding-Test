package com.inspien.application.port.out;

import com.inspien.domain.Order;

import java.util.List;

public interface OrderOutPort {
    void save(Order order);
    String findLastId();
    void markOrderSuccess(String orderId);
    List<Order> findShipmentCandidates();
}
