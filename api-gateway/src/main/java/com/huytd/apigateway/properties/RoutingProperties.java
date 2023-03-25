package com.huytd.apigateway.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "routing", ignoreUnknownFields = false)
@Getter
@Setter
public class RoutingProperties {

    private List<String> publicApis;
}
