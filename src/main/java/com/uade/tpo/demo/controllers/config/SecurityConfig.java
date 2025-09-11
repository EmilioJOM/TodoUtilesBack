package com.uade.tpo.demo.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.authentication.AuthenticationProvider;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests(auth -> auth
                                // ================= AUTH =================
                                .requestMatchers("/api/v1/auth/**").permitAll() // Registrar / Login -> GUEST USER ADMIN

                                // ================ PRODUCTOS ================
                                .requestMatchers(HttpMethod.GET, "/api/productos", "/api/productos/*", "/uploads/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/productos").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/productos/*").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/productos/add-category").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/productos/delete-category").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/productos/change-description").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/productos/add-stock").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/productos/change-price").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/productos/*/imagen").hasAuthority("ADMIN")

                                // ================ CATEGORÍAS ================
                                .requestMatchers(HttpMethod.GET, "/categories", "/categories/*").permitAll()
                                .requestMatchers(HttpMethod.POST, "/categories").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/categories/*").hasAuthority("ADMIN")

                                // ================ BÚSQUEDAS ================
                                .requestMatchers("/searches/**").permitAll()

                                // ================ CARRITO (nuevo) ================
                                .requestMatchers("/carts/**").hasAnyAuthority("USER","ADMIN")

                                // ================ CARRITO LEGACY ================
                                .requestMatchers("/carrito/**").hasAuthority("ADMIN")

                                // ================ CUPONES ================
                                .requestMatchers("/cupones/**").hasAuthority("ADMIN")

                                // ================ VENTAS ================
                                .requestMatchers(HttpMethod.GET, "/ventas", "/ventas/*").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/ventas").hasAnyAuthority("USER","ADMIN")

                                // ================= RESTO ================
                                .anyRequest().authenticated()
                        )
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}