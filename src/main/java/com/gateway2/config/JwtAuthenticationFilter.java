package com.gateway2.config;

import io.jsonwebtoken.Jwts;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
@NullMarked
public class JwtAuthenticationFilter implements GatewayFilter, Ordered {

    @Value("${jwt.secret}")
    private String SECRET;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // Authorization header-i al
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION); // tokeni goturur berarer ile birlikde sdknfsndfsnjjks.jknsdfjlsdknlslgsdlglskglsdafkajakdslgjsgd.asodfdiojfaKLKJSADLKS

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        String token = authHeader.substring(7); // bearer ignore olunur

        try {
            String id = extractUserId(token);
            ServerHttpRequest mutatedRequest = exchange.getRequest()
                    .mutate()
                    .header("user-id", id)
                    .build();

            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(mutatedRequest)
                    .build();

            return chain.filter(mutatedExchange);


        } catch (Exception e) {
            return unauthorized(exchange);
        }
    }



    // 5 HEADERDE user-id'
    // asagi request bodyde gonderilir
    /// TELEFON
    /// ELECTRONIC











    private String extractUserId(String token) {
        var claims = Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

        // Token expired-dirsə exception at
        if (claims.getExpiration().before(new Date())) {
            throw new RuntimeException("Token expired");
        }

        // Subject adətən username-dir
        return claims.getSubject();
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1; // filter-in prioriteti, kiçik rəqəm daha yüksək prioritet deməkdir
    }
}