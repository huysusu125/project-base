package com.huytd.productservice.controller.secure;

import com.huytd.productservice.dto.BaseResponse;
import com.huytd.productservice.dto.CreateProductRequest;
import com.huytd.productservice.dto.UpdatePriceProductRequest;
import com.huytd.productservice.dto.UpdateQuantityProductRequest;
import com.huytd.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/admin/product")
@RequiredArgsConstructor
public class AdminProductController {
    private final ProductService productService;

    @PostMapping
    public Mono<BaseResponse<String>> createProduct(@RequestBody CreateProductRequest createProductRequest) {
        return productService.createProduct(createProductRequest);
    }

    @PatchMapping("/price")
    public Mono<BaseResponse<Object>> updatePriceProduct(@RequestBody UpdatePriceProductRequest updatePriceProduct) {
        return productService.updatePriceProduct(updatePriceProduct);
    }

    @PatchMapping("/quantity")
    public Mono<BaseResponse<Object>> updateQuantityProduct(@RequestBody UpdateQuantityProductRequest UpdateQuantityProductRequest) {
        return productService.updateQuantityProduct(UpdateQuantityProductRequest);
    }
}
