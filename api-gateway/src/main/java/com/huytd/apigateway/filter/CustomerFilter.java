package com.huytd.apigateway.filter;

import com.huytd.apigateway.properties.RoutingProperties;
import com.huytd.apigateway.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import org.springframework.lang.NonNull;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerFilter implements WebFilter {
    private final JwtService jwtService;
    private final RoutingProperties routingProperties;

    @Override
    @NonNull
    public Mono<Void> filter(ServerWebExchange serverWebExchange, @NonNull WebFilterChain webFilterChain) {
        String requestUri = serverWebExchange.getRequest().getPath().toString();
        if (isPublicEndpointAccess(requestUri)) {
            log.info("Public api: {}", requestUri);
            return webFilterChain.filter(serverWebExchange);
        }
        HttpHeaders headers = serverWebExchange.getRequest().getHeaders();
        String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
        log.info("Private api: {}", requestUri);
        if (StringUtils.isBlank(authHeader)) {
            return completeUnauthorizedRequest(serverWebExchange);
        }
        return jwtService
                .verify(authHeader)
                .flatMap(verify -> {
                    if (verify) {
                        return webFilterChain.filter(serverWebExchange);
                    }
                    return completeUnauthorizedRequest(serverWebExchange);
                });
    }

    private Mono<Void> completeUnauthorizedRequest(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return serverWebExchange.getResponse().setComplete();
    }

    private boolean isPublicEndpointAccess(String requestUri) {
        boolean isPermitted = false;
        if (!CollectionUtils.isEmpty(routingProperties.getPublicApis())) {
            for (String healthCheckApi : routingProperties.getPublicApis()) {
                if (requestUri.matches(healthCheckApi)) {
                    isPermitted = true;
                    break;
                }
            }
        }
        return isPermitted;
    }
}

