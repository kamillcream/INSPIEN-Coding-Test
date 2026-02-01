package com.inspien.api.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.inspien.application.port.in.OrderItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemDto {

    @JacksonXmlProperty(localName = "USER_ID")
    @NotBlank(message = "USER_ID는 필수입니다.")
    private String userId;

    @JacksonXmlProperty(localName = "ITEM_ID")
    @NotBlank(message = "ITEM_ID는 필수입니다.")
    private String itemId;

    @JacksonXmlProperty(localName = "ITEM_NAME")
    @NotBlank(message = "ITEM_NAME는 필수입니다.")
    private String itemName;

    @JacksonXmlProperty(localName = "PRICE")
    @Positive(message = "PRICE는 양수여야 합니다.")
    private int price;

    public OrderItem to() {
        return new OrderItem(userId, itemId, itemName, price);
    }
}
