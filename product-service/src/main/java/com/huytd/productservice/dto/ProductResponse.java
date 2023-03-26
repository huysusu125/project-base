package com.huytd.productservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductResponse {
    private List<ProductItemResponse> productItems;
    private Long totalProduct;
}
