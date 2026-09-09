package dev.hieplp.pastebin.adapter.out.redis.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PasteCacheProperties.class)
public class RedisConfig {
}
