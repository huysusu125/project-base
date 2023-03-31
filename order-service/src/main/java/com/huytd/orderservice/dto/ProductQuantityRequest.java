package com.huytd.orderservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data @Builder
public class ProductQuantityRequest {
    List<String> productIds;
}
