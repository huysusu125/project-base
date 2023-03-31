package com.huytd.orderservice.service;

import com.huytd.orderservice.dto.CreateOrderRequest;

public interface OrderService {
    Long createOrder(CreateOrderRequest createOrderRequest);
}
