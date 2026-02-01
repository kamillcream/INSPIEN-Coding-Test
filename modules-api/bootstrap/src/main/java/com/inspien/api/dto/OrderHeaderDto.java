package com.inspien.api.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.inspien.application.port.in.OrderHeader;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderHeaderDto {

    @JacksonXmlProperty(localName = "USER_ID")
    @NotBlank(message = "USER_ID는 필수입니다.")
    private String userId;

    @JacksonXmlProperty(localName = "NAME")
    @NotBlank(message = "NAME은 필수입니다.")
    private String name;

    @JacksonXmlProperty(localName = "ADDRESS")
    @NotBlank(message = "ADDRESS는 필수입니다.")
    private String address;

    @JacksonXmlProperty(localName = "STATUS")
    @NotBlank(message = "STATUS는 필수입니다.")
    private String status;

    public OrderHeader to() {
        return new OrderHeader(userId, name, address, status);
    }
}
