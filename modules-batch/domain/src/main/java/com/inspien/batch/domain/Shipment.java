package com.inspien.batch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {

    private String shipmentId;

    private String orderId;

    private String itemId;

    private String applicantKey;

    private String address;

    public static Shipment create(String shipmentId, String orderId, String itemId, String applicantKey, String address){
        return new Shipment(shipmentId, orderId, itemId, applicantKey, address);
    }
}
