package com.huytd.orderservice.dto;

import lombok.Data;

@Data
public class OrderDetailRequest {
    private String productId;
    private Integer quantity;
}
