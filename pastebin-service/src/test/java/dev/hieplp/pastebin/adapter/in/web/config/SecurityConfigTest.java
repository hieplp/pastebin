package dev.hieplp.pastebin.adapter.in.web.config;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecurityConfigTest {

    @Test
    void corsConfigurationSource_allowsAllOriginsAndCredentials() {
        SecurityConfig config = new SecurityConfig();
        CorsConfigurationSource source = config.corsConfigurationSource();
        assertNotNull(source);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/pastes");
        var corsConfig = source.getCorsConfiguration(request);

        assertNotNull(corsConfig);
        assertTrue(Boolean.TRUE.equals(corsConfig.getAllowCredentials()));
        assertNotNull(corsConfig.getAllowedOriginPatterns());
        assertTrue(corsConfig.getAllowedOriginPatterns().contains("*"));
    }

    @Test
    void securityFilterChain_buildsSuccessfully() {
        var context = new AnnotationConfigWebApplicationContext();
        context.register(SecurityConfig.class);
        context.refresh();

        SecurityFilterChain chain = context.getBean(SecurityFilterChain.class);
        assertNotNull(chain);

        var filterClasses = chain.getFilters().stream().map(f -> f.getClass().getSimpleName()).toList();
        assertTrue(filterClasses.contains("CorsFilter"));
        assertTrue(filterClasses.stream().noneMatch(name -> name.contains("Csrf")));
        assertTrue(filterClasses.stream().noneMatch(name -> name.contains("BasicAuthentication")));
        assertTrue(filterClasses.stream().noneMatch(name -> name.contains("UsernamePasswordAuthentication")));

        context.close();
    }
}
