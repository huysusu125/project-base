package com.huytd.productservice.service.impl;

import com.huytd.productservice.Mapper.Mapper;
import com.huytd.productservice.document.Product;
import com.huytd.productservice.dto.BaseResponse;
import com.huytd.productservice.dto.CreateProductRequest;
import com.huytd.productservice.dto.ProductItemResponse;
import com.huytd.productservice.dto.ProductResponse;
import com.huytd.productservice.dto.UpdatePriceProductRequest;
import com.huytd.productservice.dto.UpdateQuantityProductRequest;
import com.huytd.productservice.repository.ProductRepository;
import com.huytd.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final Mapper<ProductItemResponse, Product, CreateProductRequest> productMapper;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Mono<BaseResponse<String>> createProduct(CreateProductRequest createProductRequest) {
        BaseResponse<String> response = new BaseResponse<>();
        return productRepository
                .save(productMapper.toDocument(createProductRequest))
                .map(product -> {
                    response.setData(product.getId());
                    return response;
                });
    }

    @Override
    public Mono<BaseResponse<ProductResponse>> getListProducts(String search, BigDecimal priceFrom, BigDecimal priceTo, Integer page, Integer size) {
        BaseResponse<ProductResponse> response = new BaseResponse<>();
        Criteria criteria = new Criteria();
        Collection<Criteria> criteriaCollection = new ArrayList<>();
        Query query = new Query();
        if (StringUtils.isNotBlank(search)) {
            // search like
            criteriaCollection.add(new Criteria()
                    .orOperator(Criteria.where("name").regex(search.trim(), "i"),
                            Criteria.where("description").regex(search.trim(), "i")));
        }
        if (priceFrom != null) {
            criteriaCollection.add(Criteria.where("price").gte(priceFrom)); // gte >= gt >
        }
        if (priceTo != null) {
            criteriaCollection.add(Criteria.where("price").lte(priceTo));   // lte <= lt >
        }
        if (!criteriaCollection.isEmpty()) {
            criteria = criteria.andOperator(criteriaCollection);
        }
        query = query.addCriteria(criteria);
        Sort sort = Sort.by(Sort.Direction.ASC, "price").and(Sort.by(Sort.Direction.ASC, "name"))
                .and(Sort.by(Sort.Direction.ASC, "description"));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return reactiveMongoTemplate
                .find(query.with(pageRequest), Product.class)
                .map(productMapper::toDto)
                .collectList()
                .zipWith(reactiveMongoTemplate.count(query, Product.class))
                .map(tuple2 -> {
                    response
                            .setData(ProductResponse
                                    .builder()
                                    .productItems(tuple2.getT1())
                                    .totalProduct(tuple2.getT2())
                                    .build());
                    return response;
                });
    }

    @Override
    public Mono<BaseResponse<Object>> updatePriceProduct(UpdatePriceProductRequest updatePriceProduct) {
        Query query = new Query(Criteria.where("_id").is(updatePriceProduct.getProductId()));
        Update update = new Update().set("price", updatePriceProduct.getNewPrice());
        return reactiveMongoTemplate
                .updateMulti(query, update, Product.class)
                .map(result -> {
                    if (result.getModifiedCount() == 0) {
                        return BaseResponse.errorResponse("Some thing error", 500);
                    }
                    return BaseResponse.builder().build();
                });
    }

    @Override
    public Mono<BaseResponse<ProductItemResponse>> getDetailProduct(String id) {
        return productRepository
                .findById(id)
                .map(product -> BaseResponse
                        .successResponse(productMapper.toDto(product)));
    }

    @Override
    public Mono<BaseResponse<Object>> updateQuantityProduct(UpdateQuantityProductRequest updatePriceProduct) {
        Query query = new Query(Criteria.where("_id").is(updatePriceProduct.getProductId()));
        Update update = new Update().inc("quantityInInventory", updatePriceProduct.getQuantity());
        return reactiveMongoTemplate
                .updateMulti(query, update, Product.class)
                .map(result -> {
                    if (result.getModifiedCount() == 0) {
                        return BaseResponse.errorResponse("Some thing error", 500);
                    }
                    return BaseResponse.builder().build();
                });
    }
}
