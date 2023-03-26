package com.huytd.productservice.controller;

import com.huytd.productservice.dto.BaseResponse;
import com.huytd.productservice.dto.CategoryResponse;
import com.huytd.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/public-api/category")
@RequiredArgsConstructor
public class PublicCategoryController {
    private final CategoryService categoryService;

    @GetMapping()
    public Mono<BaseResponse<List<CategoryResponse>>> getAlLCategories() {
        return categoryService.getAlLCategories();
    }
}
