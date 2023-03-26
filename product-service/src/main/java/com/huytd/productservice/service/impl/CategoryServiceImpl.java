package com.huytd.productservice.service.impl;

import com.huytd.productservice.Mapper.Mapper;
import com.huytd.productservice.document.Category;
import com.huytd.productservice.dto.BaseResponse;
import com.huytd.productservice.dto.CategoryResponse;
import com.huytd.productservice.dto.CreateCategoryRequest;
import com.huytd.productservice.repository.CategoryRepository;
import com.huytd.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final Mapper<CategoryResponse, Category, CreateCategoryRequest> categoryMapper;
    private final CategoryRepository categoryRepository;
    @Override
    public Mono<BaseResponse<String>> createCategory(CreateCategoryRequest createCategoryRequest) {
        BaseResponse<String> response = new BaseResponse<>();
        return categoryRepository
                .save(categoryMapper.toDocument(createCategoryRequest))
                .map(category -> {
                    response.setData(category.getId());
                    return response;
                });
    }

    @Override
    public Mono<BaseResponse<List<CategoryResponse>>> getAlLCategories() {
        BaseResponse<List<CategoryResponse>> response = new BaseResponse<>();
        return categoryRepository
                .findAll()
                .map(categoryMapper::toDto)
                .collectList()
                .map(categories -> {
                    response.setData(categories);
                    return response;
                });
    }
}
