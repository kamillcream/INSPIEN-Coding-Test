package com.inspien.api.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.inspien.application.port.in.OrderHeader;
import lombok.Data;

@Data
public class OrderHeaderDto {

    @JacksonXmlProperty(localName = "USER_ID")
    private String userId;

    @JacksonXmlProperty(localName = "NAME")
    private String name;

    @JacksonXmlProperty(localName = "ADDRESS")
    private String address;

    @JacksonXmlProperty(localName = "STATUS")
    private String status;

    public OrderHeader to() {
        return new OrderHeader(userId, name, address, status);
    }
}
