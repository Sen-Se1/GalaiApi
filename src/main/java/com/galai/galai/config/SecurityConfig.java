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

                        // Auth routes
                        .requestMatchers("/login/**", "/register/**", "/validateToken/**").permitAll()

                        // Categorie routes
                        .requestMatchers(HttpMethod.POST, "/categorie/admin/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/categorie/admin/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/categorie/admin/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/categorie/admin/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/categorie/**").permitAll()

                        // Produit routes
                        .requestMatchers(HttpMethod.POST, "/produit/admin/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/produit/admin/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/produit/admin/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/produit/admin/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/produit/**").permitAll()

                        // Commande routes
                        .requestMatchers(HttpMethod.POST, "/commande/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/commande/admin/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/commande/admin/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/commande/**").permitAll()

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
