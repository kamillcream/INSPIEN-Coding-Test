package com.inspien.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateCommand {
    private List<OrderHeader> headers;
    private List<OrderItem> items;
}
