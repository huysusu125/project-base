package com.huytd.apigateway.filter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class CustomerFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        String requestUri = serverWebExchange.getRequest().getPath().toString();
        if (isPublicEndpointAccess(requestUri)) {

        }
        return completeUnauthorizedRequest(serverWebExchange);
    }

    private Mono<Void> completeUnauthorizedRequest(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return serverWebExchange.getResponse().setComplete();
    }

    private boolean isPublicEndpointAccess(String requestUri) {
        boolean isPermitted = false;
        if(!CollectionUtils.isEmpty(routingProperties.getPublicApis())) {
            for(String healthCheckApi: routingProperties.getPublicApis()) {
                if(requestUri.matches(healthCheckApi)) {
                    isPermitted = true;
                    break;
                }
            }
        }
        return isPermitted;
    }
}

