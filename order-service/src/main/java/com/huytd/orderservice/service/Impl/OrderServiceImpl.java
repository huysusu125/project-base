package com.huytd.orderservice.service.Impl;

import com.huytd.orderservice.constant.ErrorCodes;
import com.huytd.orderservice.constant.OrderStatusEnum;
import com.huytd.orderservice.dto.BaseResponse;
import com.huytd.orderservice.dto.CreateOrderRequest;
import com.huytd.orderservice.dto.OrderDetailRequest;
import com.huytd.orderservice.dto.ProductQuantityRequest;
import com.huytd.orderservice.dto.ProductQuantityResponse;
import com.huytd.orderservice.entity.Order;
import com.huytd.orderservice.entity.OrderDetail;
import com.huytd.orderservice.exception.BadRequestException;
import com.huytd.orderservice.repository.OrderDetailRepository;
import com.huytd.orderservice.repository.OrderRepository;
import com.huytd.orderservice.service.OrderService;
import com.huytd.orderservice.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final JwtTokenUtils jwtTokenUtils;

    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final WebClient.Builder webClientBuilder;

    @Value("${url-internal.is-stock-product}")
    private String urlCheckStockProduct;

    @SneakyThrows
    @Override
    @Transactional
    public Long createOrder(CreateOrderRequest createOrderRequest) {
        Long userId = jwtTokenUtils.getUserIdFromToken();
        // call api to product-service check quantity product

        Mono<ProductQuantityRequest> productQuantityRequest = Mono.just(ProductQuantityRequest
                .builder()
                .productIds(createOrderRequest
                        .getOrdersDetailsRequest()
                        .stream()
                        .map(OrderDetailRequest::getProductId)
                        .collect(Collectors.toList()))
                .build());

        ProductQuantityResponse[] responseProduct = webClientBuilder
                .build()
                .post()
                .uri(urlCheckStockProduct)
                .body(productQuantityRequest, ProductQuantityRequest.class)
                .retrieve()
                .bodyToMono(ProductQuantityResponse[].class)
                .block();

        if (responseProduct == null
                || !Arrays.stream(responseProduct)
                .allMatch(ProductQuantityResponse::getIsStock)) {
            throw new BadRequestException(Collections.singletonList(ErrorCodes.PRODUCT_IS_NOT_AVAILABLE));
        }

        Order order = orderRepository.save(Order
                .builder()
                .address(createOrderRequest.getAddress())
                .userId(userId)
                .customerName(createOrderRequest.getCustomerName())
                .paymentMethod(createOrderRequest.getPaymentMethod())
                .orderTotalValue(BigDecimal.ZERO)
                .status(OrderStatusEnum.PENDING.getCode())
                .build());

        List<OrderDetail> orderDetails = createOrderRequest
                .getOrdersDetailsRequest()
                .stream()
                .map(orderDetailRequest -> OrderDetail
                        .builder()
                        .productId(orderDetailRequest.getProductId())
                        .quantity(orderDetailRequest.getQuantity())
                        .order(order)
                        .build())
                .collect(Collectors.toList());

        orderDetailRepository.saveAll(orderDetails);

        return order.getId();
    }
}
