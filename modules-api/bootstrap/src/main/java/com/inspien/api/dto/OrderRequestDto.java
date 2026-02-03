package com.inspien.api.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.inspien.api.application.port.in.OrderCreateCommand;
import com.inspien.api.application.port.in.OrderHeader;
import com.inspien.api.application.port.in.OrderItem;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "ORDER")
public class OrderRequestDto {

    @JacksonXmlProperty(localName = "HEADER")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<OrderHeaderDto> headers;

    @JacksonXmlProperty(localName = "ITEM")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<OrderItemDto> items;

    public OrderCreateCommand toCommand(){
        List<OrderHeader> orderHeaders = this.headers == null
                ? List.of()
                : this.headers.stream().map(OrderHeaderDto::to).toList();
        List<OrderItem> orderItems = this.items == null
                ? List.of()
                : this.items.stream().map(OrderItemDto::to).toList();

        return new OrderCreateCommand(
                orderHeaders, orderItems
        );
    }
}

