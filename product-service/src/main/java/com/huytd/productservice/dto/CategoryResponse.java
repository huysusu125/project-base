package com.huytd.productservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponse {
    private String categoryName;
    private String description;
}
