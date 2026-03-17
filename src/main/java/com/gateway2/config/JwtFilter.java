package com.gateway2.config;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtFilter {


    @Value("${jwt.secret}")
    private String SECRET;

        public String extractUsername(String token){
            var jwtPayload = Jwts.parserBuilder().
                    setSigningKey(SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            var userId = jwtPayload.getSubject();
            var expireDate= jwtPayload.getExpiration();
            if(expireDate.before(new Date())){
                throw new RuntimeException("token vaxdi bitib");
            }
            return userId;




    }

}
