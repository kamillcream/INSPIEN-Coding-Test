package com.inspien.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderShipmentPayload {
    private String orderId;

    private String itemId;

    private String applicantKey;

    private String address;

    public static OrderShipmentPayload create(String orderId, String itemId, String applicantKey, String address) {
        return new OrderShipmentPayload(orderId, itemId, applicantKey, address);
    }
}
