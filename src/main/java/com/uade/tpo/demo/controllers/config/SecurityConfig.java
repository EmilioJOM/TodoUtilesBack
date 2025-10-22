package com.uade.tpo.demo.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
                        .cors(Customizer.withDefaults()) // 👈 HABILITA CORS
                        .authorizeHttpRequests(auth -> auth
                                // permitir preflight global
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // 👈 OPCIONES
                                // Auth pública
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                // Productos
                                .requestMatchers(HttpMethod.GET, "/api/productos", "/api/productos/*", "/uploads/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/productos").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/productos/*").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/productos/add-category").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/productos/delete-category").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/productos/change-description").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/productos/add-stock").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/productos/change-price").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/productos/*/imagen").hasAuthority("ADMIN")
                                // Categorías
                                .requestMatchers(HttpMethod.GET, "/categories", "/categories/*").permitAll()
                                .requestMatchers(HttpMethod.POST, "/categories").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/categories/*").hasAuthority("ADMIN")
                                // Búsquedas
                                .requestMatchers("/searches/**").permitAll()
                                // Carrito
                                .requestMatchers("/carts/**").hasAnyAuthority("USER","ADMIN")
                                // Legacy
                                .requestMatchers("/carrito/**").hasAuthority("ADMIN")
                                // Cupones
                                .requestMatchers("/cupones/**").hasAuthority("ADMIN")
                                // Ventas
                                .requestMatchers(HttpMethod.GET, "/ventas", "/ventas/*").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/ventas").hasAnyAuthority("USER","ADMIN")
                                // Resto
                                .anyRequest().authenticated()
                        )
                        .sessionManagement(s -> s.sessionCreationPolicy(STATELESS))
                        .authenticationProvider(authenticationProvider)
                        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration cfg = new CorsConfiguration();
                // tu Vite dev server
                cfg.setAllowedOriginPatterns(List.of("http://localhost:*"));
                cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
                cfg.setAllowedHeaders(List.of("Content-Type","Authorization","X-Requested-With","Accept","Origin"));
                cfg.setExposedHeaders(List.of("Authorization")); // si exponés el JWT en header
                cfg.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", cfg);
                return source;
        }
}
