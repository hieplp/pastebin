package dev.hieplp.pastebin.adapter.out.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "pastebin.cache")
public record PasteCacheProperties(Duration ttl) {
}
