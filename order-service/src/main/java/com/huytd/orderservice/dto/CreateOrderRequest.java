package com.huytd.orderservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    private Integer paymentMethod;
    private String address;
    private String customerName;
    List<OrderDetailRequest> ordersDetailsRequest;
}
