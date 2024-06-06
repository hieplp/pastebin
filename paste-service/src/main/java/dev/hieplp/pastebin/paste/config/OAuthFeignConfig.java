package dev.hieplp.pastebin.paste.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class OAuthFeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
        };
    }
}
