package com.huytd.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryRequest {
    @NotBlank(message = "categoryName is required")
    private String categoryName;
    private String description;
}
