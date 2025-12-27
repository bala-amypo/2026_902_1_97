package com.example.demo.config;
import com.example.demo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class JwtConfig {
    @Value("${jwt.secret:abcdefghijklmnopqrstuvwxyz0123456789ABCD}")
    private String secret;
    @Value("${jwt.expiration:3600000}")
    private Long expiration;
    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(secret, expiration);
    }
}