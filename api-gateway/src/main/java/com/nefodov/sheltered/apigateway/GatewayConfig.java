package com.nefodov.sheltered.apigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final AuthenticationFilter filter;

    @Autowired
    public GatewayConfig(AuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("web_route",  r -> r
                        .path("/web-service/**")
                        .uri("http://web-service:8081"))
                .route("shelter_route_protected", r -> r
                        .path("/shelter-service/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://shelter-service:8082"))
                .route("user_route", r -> r
                        .path("/user-service/**")
                        .uri("http://user-service:8083"))
                .build();
    }
}
