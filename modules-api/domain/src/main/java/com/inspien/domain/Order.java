package com.inspien.domain;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String orderId;

    private String userId;

    private String itemId;

    private String applicantKey;

    private String name;

    private String address;

    private String itemName;

    private String price;

    private String status;

    public static Order create(String orderId, String userId, String itemId, String applicantKey, String name,
                               String address, String itemName, String price, String status) {
        return Order.builder()
                .orderId(orderId)
                .userId(userId)
                .itemId(itemId)
                .applicantKey(applicantKey)
                .name(name)
                .address(address)
                .itemName(itemName)
                .price(price)
                .status(status)
                .build();

    }
}
