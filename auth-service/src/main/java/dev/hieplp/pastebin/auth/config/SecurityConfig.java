package dev.hieplp.pastebin.auth.config;

import dev.hieplp.pastebin.common.auth.AbstractJwtAuthEntryPoint;
import dev.hieplp.pastebin.common.auth.AbstractJwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AbstractJwtAuthFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    private final AbstractJwtAuthEntryPoint jwtAuthEntryPoint;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)

                // Register & Login
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                        "/auth/register", "/auth/login"
                ).permitAll())

                // Swagger
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                        "/actuator/**",
                        "/swagger-ui", "/swagger-ui/**", "/error", "/v3/api-docs/**"
                ).permitAll())

                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authenticationProvider(authenticationProvider)

                // Entry point
                .exceptionHandling(exc -> exc.authenticationEntryPoint(jwtAuthEntryPoint))

                // Filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

}
