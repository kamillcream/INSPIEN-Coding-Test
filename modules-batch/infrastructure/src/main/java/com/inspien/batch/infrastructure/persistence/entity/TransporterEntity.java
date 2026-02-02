package com.inspien.batch.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SHIPMENT_TB")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransporterEntity {
    @Id
    @Column(name = "SHIPMENT_ID")
    private String shipmentId;

    @Column(name = "ORDER_ID")
    private String orderId;

    @Column(name = "ITEM_ID")
    private String itemId;

    @Column(name = "APPLICANT_KEY")
    private String applicantKey;

    @Column(name = "ADDRESS")
    private String address;
}
