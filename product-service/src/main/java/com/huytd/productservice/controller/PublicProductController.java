package com.huytd.productservice.controller;

import com.huytd.productservice.dto.BaseResponse;
import com.huytd.productservice.dto.ProductResponse;
import com.huytd.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/public-api/product")
@RequiredArgsConstructor
public class PublicProductController {
    private final ProductService productService;

    @GetMapping()
    public Mono<BaseResponse<ProductResponse>> getListProducts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) BigDecimal priceFrom,
            @RequestParam(required = false) BigDecimal priceTo,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "12") Integer size
    ) {
        return productService.getListProducts(search, priceFrom, priceTo, page, size);
    }
}
