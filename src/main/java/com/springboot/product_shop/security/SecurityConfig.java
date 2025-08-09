package com.springboot.product_shop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf-> csrf.disable())
                //.httpBasic(Customizer.withDefaults())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize-> authorize
                        //For Swagger and OpenAPI (Allow access to Swagger and OpenAPI without authentication)
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        //For Role class
                        .requestMatchers(HttpMethod.GET,"/api/v1/roles").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/roles/{id}").hasRole("ADMIN")
                        //For User class
                        .requestMatchers(HttpMethod.GET, "/api/v1/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/self").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/by-document").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/by-username").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/by-username-containing").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/by-full-name-containing").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/customer/self").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/self").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}").hasRole("ADMIN")
                        //For Category class
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/by-name").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/by-name-containing").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/categories").hasAnyRole("MANAGER","ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/categories/{id}").hasAnyRole("MANAGER","ADMIN")
                        //For Product class
                        .requestMatchers(HttpMethod.GET, "/api/v1/products").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/by-name").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/by-name-containing").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/{categoryId}/products").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/by-price-between").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/by-stock-quantity-between").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/products").hasAnyRole("MANAGER","ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/products/{id}").hasAnyRole("MANAGER","ADMIN")
                        //For Order class
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders/self").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders").hasAnyRole("MANAGER","ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders/self/{id}").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders/{id}").hasAnyRole("MANAGER","ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders/by-user/{userId}").hasAnyRole("MANAGER","ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/orders").hasRole("CUSTOMER")//Note: Only customers can order
                        //For OrderProduct class
                        .requestMatchers(HttpMethod.GET, "/api/v1/order-products").hasAnyRole("ADMIN","MANAGER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/order-products/by-id").hasAnyRole("ADMIN","MANAGER")
                        .anyRequest().authenticated())
                .exceptionHandling(e->e
                        .accessDeniedHandler(customAccessDeniedHandler)//Handles 403 Forbidden
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))//Handles 401 Unauthorized
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
