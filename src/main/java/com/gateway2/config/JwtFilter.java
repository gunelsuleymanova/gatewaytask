//package com.gateway2.config;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.security.Key;
//import java.util.Date;
//
//@Component
//public class JwtFilter implements GatewayFilter {
//
//    @Value("${jwt.secret}")
//    private String SECRET;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        try {
//            String authHeader = exchange.getRequest()
//                    .getHeaders()
//                    .getFirst("Authorization");
//
//            String token = extractJwtFromRequest(authHeader);
//
//            if (token == null) {
//                return unauthorized(exchange);
//            }
//
//            String username = extractUsername(token);
//
//            // user məlumatını header-a əlavə edirik (optional)
//            exchange.getRequest().mutate()
//                    .header("X-User", username)
//                    .build();
//
//        } catch (Exception e) {
//            return unauthorized(exchange);
//        }
//
//        return chain.filter(exchange);
//    }
//
//    // ================= HELPER METHODS =================
//
//    private String extractJwtFromRequest(String authHeader) {
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            return authHeader.substring(7);
//        }
//        return null;
//    }
//
//    private String extractUsername(String token) {
//        var claims = Jwts.parserBuilder()
//                .setSigningKey(getSigningKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//
//        if (claims.getExpiration().before(new Date())) {
//            throw new RuntimeException("Token expired");
//        }
//
//        return claims.getSubject();
//    }
//
//    private Key getSigningKey() {
//        return Keys.hmacShaKeyFor(SECRET.getBytes());
//    }
//
//    private Mono<Void> unauthorized(ServerWebExchange exchange) {
//        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//        return exchange.getResponse().setComplete();
//    }
//}