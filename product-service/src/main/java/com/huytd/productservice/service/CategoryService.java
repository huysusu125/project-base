package com.huytd.productservice.service;

import com.huytd.productservice.dto.BaseResponse;
import com.huytd.productservice.dto.CategoryResponse;
import com.huytd.productservice.dto.CreateCategoryRequest;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CategoryService {
    Mono<BaseResponse<String>> createCategory(CreateCategoryRequest createCategoryRequest);

    Mono<BaseResponse<List<CategoryResponse>>> getAlLCategories();
}
