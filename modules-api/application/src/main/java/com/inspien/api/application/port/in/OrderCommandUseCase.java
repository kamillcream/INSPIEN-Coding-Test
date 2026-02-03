package com.inspien.api.application.port.in;

public interface OrderCommandUseCase {
    void processOrder(OrderCreateCommand command);
}
