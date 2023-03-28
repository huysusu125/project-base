package com.huytd.productservice.dto;

import lombok.Data;


@Data
public class UpdateQuantityProductRequest {
    private String productId;
    private Long quantity;
}
