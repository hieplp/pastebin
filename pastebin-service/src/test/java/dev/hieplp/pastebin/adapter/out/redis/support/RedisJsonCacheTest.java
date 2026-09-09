package dev.hieplp.pastebin.adapter.out.redis.support;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedisJsonCacheTest {

    @Mock
    private StringRedisTemplate redis;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ValueOperations<String, String> values;

    private RedisJsonCache cache;

    @BeforeEach
    void setUp() {
        cache = new RedisJsonCache(redis, objectMapper);
    }

    @Test
    void get_whenRedisDown_returnsEmpty() {
        when(redis.opsForValue()).thenReturn(values);
        when(values.get("paste:p-1")).thenThrow(new RuntimeException("down"));

        assertEquals(Optional.empty(), cache.get("paste:p-1", String.class));
    }

    @Test
    void get_hit_deserializes() {
        when(redis.opsForValue()).thenReturn(values);
        when(values.get("k")).thenReturn("{}");
        when(objectMapper.readValue("{}", String.class)).thenReturn("ok");

        assertEquals(Optional.of("ok"), cache.get("k", String.class));
    }

    @Test
    void put_writesKeys() throws Exception {
        when(redis.opsForValue()).thenReturn(values);
        when(objectMapper.writeValueAsString("v")).thenReturn("{}");

        cache.put("v", Duration.ofHours(1), "a", "b");

        verify(values).set("a", "{}", Duration.ofHours(1));
        verify(values).set("b", "{}", Duration.ofHours(1));
    }

    @Test
    void put_whenRedisDown_doesNotThrow() throws Exception {
        when(objectMapper.writeValueAsString("v")).thenReturn("{}");
        when(redis.opsForValue()).thenThrow(new RuntimeException("down"));

        cache.put("v", Duration.ofHours(1), "a");

        verify(values, never()).set(any(), any(), any(Duration.class));
    }

    @Test
    void delete_whenRedisDown_doesNotThrow() {
        when(redis.delete(List.of("a"))).thenThrow(new RuntimeException("down"));

        cache.delete(List.of("a"));
    }
}
