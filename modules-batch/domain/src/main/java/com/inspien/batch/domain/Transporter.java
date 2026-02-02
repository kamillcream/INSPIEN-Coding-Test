package com.inspien.batch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transporter {

    private String shipmentId;

    private String orderId;

    private String itemId;

    private String applicantKey;

    private String address;
}
