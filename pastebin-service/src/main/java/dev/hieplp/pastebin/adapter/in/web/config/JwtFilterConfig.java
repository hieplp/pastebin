package dev.hieplp.pastebin.adapter.in.web.config;

import dev.hieplp.pastebin.adapter.in.web.security.AuthCookieWriter;
import dev.hieplp.pastebin.adapter.in.web.security.JwtAuthenticationFilter;
import dev.hieplp.pastebin.application.port.out.token.ParseTokenPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtFilterConfig {

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter(AuthCookieWriter authCookieWriter, ParseTokenPort parseTokenPort) {
        return new JwtAuthenticationFilter(authCookieWriter, parseTokenPort);
    }

}
