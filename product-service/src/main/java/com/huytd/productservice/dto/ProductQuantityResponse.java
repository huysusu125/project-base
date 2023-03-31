package com.huytd.productservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductQuantityResponse {
    private Long quantity;
    private BigDecimal price;
    private Boolean isStock;
}
