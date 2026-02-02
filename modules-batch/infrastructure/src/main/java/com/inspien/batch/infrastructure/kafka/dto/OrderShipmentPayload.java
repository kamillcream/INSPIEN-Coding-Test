package com.inspien.batch.infrastructure.kafka.dto;

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
}
