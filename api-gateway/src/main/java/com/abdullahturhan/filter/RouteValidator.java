package com.abdullahturhan.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class RouteValidator {
    public static final List<String> publicEndpoints = List.of(
            "/auth/register",
            "/auth/token",
            "/auth/validate",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured
            = request -> publicEndpoints.stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
