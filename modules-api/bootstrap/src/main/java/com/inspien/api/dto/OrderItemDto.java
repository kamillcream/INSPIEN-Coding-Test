package com.inspien.api.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.inspien.application.port.in.OrderItem;
import lombok.Data;

@Data
public class OrderItemDto {

    @JacksonXmlProperty(localName = "USER_ID")
    private String userId;

    @JacksonXmlProperty(localName = "ITEM_ID")
    private String itemId;

    @JacksonXmlProperty(localName = "ITEM_NAME")
    private String itemName;

    @JacksonXmlProperty(localName = "PRICE")
    private int price;

    public OrderItem to() {
        return new OrderItem(userId, itemId, itemName, price);
    }
}
