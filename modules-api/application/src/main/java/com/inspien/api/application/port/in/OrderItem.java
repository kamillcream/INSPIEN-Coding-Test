package com.inspien.api.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private String userId;

    private String itemId;

    private String itemName;

    private int price;
}
