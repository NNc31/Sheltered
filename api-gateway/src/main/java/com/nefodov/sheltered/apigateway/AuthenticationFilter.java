package com.nefodov.sheltered.apigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthenticationFilter implements GatewayFilter {

    private final JwtService jwtService;

    @Autowired
    public AuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
        protectedPaths = new ArrayList<>();
        protectedPaths.add("/shelter-service/add");
        protectedPaths.add("/shelter-service/update");
        protectedPaths.add("/shelter-service/delete");
    }

    private final List<String> protectedPaths;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (protectedPaths.contains(request.getPath().toString())) {
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return this.onError(exchange, "Missing Authorization Header", HttpStatus.UNAUTHORIZED);
            }
            String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");

            if (jwtService.validateToken(token)) {
                return chain.filter(exchange);
            } else {
                return this.onError(exchange, "Invalid token", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return chain.filter(exchange);
        }
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }
}

