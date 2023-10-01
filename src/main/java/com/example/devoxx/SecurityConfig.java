package com.example.devoxx;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPublicKey;

@Configuration
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain supportSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/support/tickets")
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().hasAuthority("SCOPE_support")
                )
                .oauth2ResourceServer(oauth -> oauth
                        .jwt(Customizer.withDefaults())
                );
        return http.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/tour").permitAll()
                        .requestMatchers("/show").hasAuthority("SWIFTIE")
                        .anyRequest().authenticated()
                );
        return http.build();
    }


    @Value("${jwt.public.key}")
    RSAPublicKey key;

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }
}
