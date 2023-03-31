package com.huytd.orderservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductQuantityResponse {
    private Long quantity;
    private BigDecimal price;
    private Boolean isStock;
}
