package dev.hieplp.pastebin.adapter.out.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "pastebin.jwt")
public record JwtProperties(
        String issuer,
        Duration accessTtl,
        Duration refreshTtl,
        String accessCookieName,
        String refreshCookieName,
        boolean cookieSecure,
        String cookieSameSite,
        String cookiePath,
        String privateKey,
        String publicKey
) {
}
