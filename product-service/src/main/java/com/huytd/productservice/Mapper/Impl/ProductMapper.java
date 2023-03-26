package com.huytd.productservice.Mapper.Impl;

import com.huytd.productservice.Mapper.Mapper;
import com.huytd.productservice.document.Product;
import com.huytd.productservice.dto.CreateProductRequest;
import com.huytd.productservice.dto.ProductItemResponse;
import com.huytd.productservice.dto.ProductResponse;
import org.springframework.stereotype.Component;


@Component("productMapper")
public class ProductMapper implements Mapper<ProductItemResponse, Product, CreateProductRequest> {
    @Override
    public ProductItemResponse toDto(Product document) {
        return ProductItemResponse
                .builder()
                .productId(document.getId())
                .categoryId(document.getCategoryId())
                .image(document.getImage())
                .price(document.getPrice())
                .name(document.getName())
                .description(document.getDescription())
                .quantityInInventory(document.getQuantityInInventory())
                .build();
    }

    @Override
    public Product toDocument(CreateProductRequest request) {
        return Product
                .builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .image(request.getImage())
                .categoryId(request.getCategoryId())
                .build();
    }
}
