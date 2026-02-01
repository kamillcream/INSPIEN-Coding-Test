package com.inspien.application.port.out;

import com.inspien.domain.Order;

public interface OrderOutPort {
    void save(Order order);
    String findLastId();
}
