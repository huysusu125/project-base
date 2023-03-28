package com.huytd.productservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdatePriceProductRequest {
    private String productId;
    private BigDecimal newPrice;
}
