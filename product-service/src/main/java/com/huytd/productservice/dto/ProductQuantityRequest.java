package com.huytd.productservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductQuantityRequest {
    List<String> productIds;
}
