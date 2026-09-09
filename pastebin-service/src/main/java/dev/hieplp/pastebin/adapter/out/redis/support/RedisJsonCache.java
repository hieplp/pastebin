package dev.hieplp.pastebin.adapter.out.redis.support;
import dev.hieplp.pastebin.domain.util.Strings;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisJsonCache {

    private final StringRedisTemplate redis;

    private final ObjectMapper objectMapper;

    public <T> Optional<T> get(String key, Class<T> type) {
        try {
            var json = Strings.trim(redis.opsForValue().get(key)).orElse(null);
            if (json == null) {
                return Optional.empty();
            }

            return Optional.of(objectMapper.readValue(json, type));
        } catch (RuntimeException e) {
            log.warn("Redis get failed for {}: {}", key, e.getMessage());
            return Optional.empty();
        }
    }

    public void put(Object value, Duration ttl, String... keys) {
        if (value == null || ttl == null || ttl.isZero() || ttl.isNegative() || keys == null || keys.length == 0) {
            return;
        }

        try {
            var json = objectMapper.writeValueAsString(value);
            var values = redis.opsForValue();
            for (var key : keys) {
                Strings.trim(key).ifPresent(k -> values.set(k, json, ttl));
            }
        } catch (RuntimeException e) {
            log.warn("Redis put failed for {}: {}", keys[0], e.getMessage());
        }
    }

    public void delete(Collection<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return;
        }

        try {
            redis.delete(keys);
        } catch (RuntimeException e) {
            log.warn("Redis delete failed: {}", e.getMessage());
        }
    }

}
