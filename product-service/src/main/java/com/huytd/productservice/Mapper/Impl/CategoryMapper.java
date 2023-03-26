package com.huytd.productservice.Mapper.Impl;

import com.huytd.productservice.Mapper.Mapper;
import com.huytd.productservice.document.Category;
import com.huytd.productservice.dto.CategoryResponse;
import com.huytd.productservice.dto.CreateCategoryRequest;
import org.springframework.stereotype.Component;

@Component("categoryMapper")
public class CategoryMapper implements Mapper<CategoryResponse, Category, CreateCategoryRequest> {
    @Override
    public CategoryResponse toDto(Category document) {
        return CategoryResponse
                .builder()
                .categoryName(document.getName())
                .description(document.getDescription())
                .build();
    }

    @Override
    public Category toDocument(CreateCategoryRequest request) {
        return Category
                .builder()
                .name(request.getCategoryName())
                .description(request.getDescription())
                .build();
    }
}
