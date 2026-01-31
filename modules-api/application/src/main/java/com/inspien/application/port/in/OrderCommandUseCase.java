package com.inspien.application.port.in;

public interface OrderCommandUseCase {
    void processOrder(OrderCreateCommand command);
}
