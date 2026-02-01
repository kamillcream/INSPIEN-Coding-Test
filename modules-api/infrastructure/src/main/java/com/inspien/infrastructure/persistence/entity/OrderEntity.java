package com.inspien.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    @Column(name = "ORDER_ID")
    private String orderId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ITEM_ID")
    private String itemId;

    @Column(name = "APPLICANT_KEY")
    private String applicantKey;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "PRICE")
    private String price;

    @Column(name = "STATUS")
    private String status;
}
