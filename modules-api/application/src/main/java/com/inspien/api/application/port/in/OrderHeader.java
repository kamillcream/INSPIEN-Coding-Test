package com.inspien.api.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderHeader {
    private String userId;

    private String name;

    private String address;

    private String status;
}
