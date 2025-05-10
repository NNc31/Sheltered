package com.nefodov.sheltered.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("web_route",  r -> r
                        .path("/web-service/**")
                        .uri("http://web-service:8081"))
                .route("shelter_route", r -> r
                        .path("/shelter-service/**")
                        .uri("http://shelter-service:8082"))
                .route("supply_route", r -> r
                        .path("/supply-service/**")
                        .uri("http://supply-service:8083"))
                .build();
    }
}
