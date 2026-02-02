package com.inspien.batch.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderShipmentCommand {
    private String orderId;

    private String itemId;

    private String applicantKey;

    private String address;

    public static OrderShipmentCommand create(String orderId, String itemId, String applicantKey, String address) {
        return new OrderShipmentCommand(orderId, itemId, applicantKey, address);
    }
}