package com.huytd.orderservice.controller;

import com.huytd.orderservice.dto.BaseResponse;
import com.huytd.orderservice.dto.CreateOrderRequest;
import com.huytd.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public BaseResponse<Long> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        BaseResponse<Long> response = new BaseResponse<>();
        response.setData(orderService.createOrder(createOrderRequest));
        return response;
    }
}
