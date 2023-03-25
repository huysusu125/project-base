package com.huytd.apigateway.service;

import reactor.core.publisher.Mono;

public interface JwtService {
    Mono<Boolean> verify(String token);
}
