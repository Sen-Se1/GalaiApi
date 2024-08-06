package com.galai.galai.config;

import com.galai.galai.Service.UserDetailsServiceImp;
import com.galai.galai.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsServiceImp userDetailsServiceImp;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp , JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(corsConfigurer -> {
                    corsConfigurer.configurationSource(corsConfigurationSource());
                })
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/login/**", "/register/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/prix/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/produit/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/produit/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/produit/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/produit/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/categorie/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/categorie/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/categorie/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/categorie/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/commande/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/commande/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/commande/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/commande/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsServiceImp)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("null", "http://localhost:8088")); // Add "null" to the allowed origins
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true); // Add this line to allow credentials
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
