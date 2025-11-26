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
import org.springframework.security.authentication.AuthenticationProvider;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
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
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
                // 1️⃣ AUTH pública (siempre primero)
                .requestMatchers("/api/v1/auth/**").permitAll()

                // 2️⃣ Recursos estáticos públicos (Para imágenes subidas)
                .requestMatchers("/uploads/**").permitAll()
                .requestMatchers("/images/**").permitAll()

                // REGLAS DE LA RAMA MAIN
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
                .requestMatchers(HttpMethod.GET,"/cupones/**").hasAnyAuthority("USER","ADMIN")
                .requestMatchers("/cupones/**").hasAnyAuthority("ADMIN")
                // Ventas
                .requestMatchers(HttpMethod.GET, "/ventas").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/ventas/my").hasAnyAuthority("USER")
                .requestMatchers(HttpMethod.POST, "/ventas").hasAnyAuthority("USER","ADMIN")
                
                // REGLAS TUYAS PARA PRODUCTOS
                // 3️⃣ Productos públicos (solo GET)
                .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()

                // 4️⃣ Permisos ADMIN
                .requestMatchers(HttpMethod.POST, "/api/productos").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/productos/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasAuthority("ADMIN")

                // 5️⃣ Endpoint de subida de imágenes (Permitido para testear o si el producto lo requiere)
                .requestMatchers(HttpMethod.POST, "/api/productos/*/imagen").permitAll()
                
                // 6️⃣ El resto requiere autenticación
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
        cfg.setAllowedOriginPatterns(List.of("http://localhost:5173", "http://localhost:5174"));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
        cfg.setAllowedHeaders(List.of("Content-Type","Authorization","X-Requested-With","Accept","Origin"));
        cfg.setExposedHeaders(List.of("Authorization"));
        cfg.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}