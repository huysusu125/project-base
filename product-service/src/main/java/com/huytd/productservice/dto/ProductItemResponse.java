package com.huytd.productservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductItemResponse {
    private String productId;

    private String name;

    private String description;

    private BigDecimal price;

    private String image;

    private String categoryId;

    private Long quantityInInventory;
}
