package com.huytd.productservice.controller.internal;


import com.huytd.productservice.dto.ProductQuantityRequest;
import com.huytd.productservice.dto.ProductQuantityResponse;
import com.huytd.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/internal-api/product")
@RequiredArgsConstructor
public class InternalProductController {

    private final ProductService productService;

    @PostMapping("/stock")
    public Flux<ProductQuantityResponse> checkQuantityIsStock(
            @RequestBody ProductQuantityRequest productQuantityRequest
    ) {
        return productService.checkQuantityIsStock(productQuantityRequest);
    }

}
