package com.huytd.productservice.service;

import com.huytd.productservice.dto.BaseResponse;
import com.huytd.productservice.dto.CreateProductRequest;
import com.huytd.productservice.dto.ProductItemResponse;
import com.huytd.productservice.dto.ProductQuantityRequest;
import com.huytd.productservice.dto.ProductQuantityResponse;
import com.huytd.productservice.dto.ProductResponse;
import com.huytd.productservice.dto.UpdatePriceProductRequest;
import com.huytd.productservice.dto.UpdateQuantityProductRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Mono<BaseResponse<String>> createProduct(CreateProductRequest createProductRequest);

    Mono<BaseResponse<ProductResponse>> getListProducts(String search,
                                                        BigDecimal priceFrom,
                                                        BigDecimal priceTo,
                                                        Integer page,
                                                        Integer size);

    Mono<BaseResponse<Object>> updatePriceProduct(UpdatePriceProductRequest updatePriceProduct);

    Mono<BaseResponse<ProductItemResponse>> getDetailProduct(String id);

    Mono<BaseResponse<Object>> updateQuantityProduct(UpdateQuantityProductRequest updatePriceProduct);

    Flux<ProductQuantityResponse> checkQuantityIsStock(ProductQuantityRequest productQuantityRequest);
}
