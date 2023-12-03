package com.abdullahturhan.config;

import com.abdullahturhan.filter.AuthenticationFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
public class GatewayConfig {
    private final AuthenticationFilter filter;

    public GatewayConfig(AuthenticationFilter filter) {
        this.filter = filter;
    }
    @Bean
    public RouteLocator locator(RouteLocatorBuilder builder){
            return builder.routes()
                    .route("user-service", r -> r.path("/api/users/**")
                            .filters(f -> f.filter(filter))
                            .uri("lb://USER-SERVICE"))
                    .route("task-management-service", r -> r.path("/api/tasks/**")
                            .filters(f -> f.filter(filter))
                            .uri("lb://TASK-MANAGEMENT-SERVICE"))
                    .route("security-service", r -> r.path("/auth/**")
                            .filters(f -> f.filter(filter))
                            .uri("lb://SECURITY-SERVICE"))
                    .build();
    }
}


/*

 */
