package com.huytd.productservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {
    private String name;

    private String description;

    private BigDecimal price;

    private String image;

    private String categoryId;
}
