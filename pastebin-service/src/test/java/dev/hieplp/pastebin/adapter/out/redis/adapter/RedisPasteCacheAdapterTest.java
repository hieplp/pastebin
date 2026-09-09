package dev.hieplp.pastebin.adapter.out.redis.adapter;

import dev.hieplp.pastebin.adapter.out.redis.config.PasteCacheProperties;
import dev.hieplp.pastebin.adapter.out.redis.support.RedisJsonCache;
import dev.hieplp.pastebin.application.dto.paste.result.PasteResult;
import dev.hieplp.pastebin.domain.enums.PasteStatus;
import dev.hieplp.pastebin.domain.enums.Privacy;
import dev.hieplp.pastebin.domain.enums.Syntax;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedisPasteCacheAdapterTest {

    @Mock
    private RedisJsonCache cache;

    private RedisPasteCacheAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new RedisPasteCacheAdapter(new PasteCacheProperties(Duration.ofHours(1)), cache);
    }

    @Test
    void put_writesIdAndAliasKeys() {
        var result = new PasteResult("p-1", "t", "hello", "c", Privacy.PUBLIC, Syntax.PLAINTEXT, null, null, PasteStatus.ACTIVE, false, List.of());

        adapter.put(result);

        verify(cache).put(result, Duration.ofHours(1), "paste:p-1", "paste:hello");
    }

    @Test
    void get_hit_deserializes() {
        var result = new PasteResult("p-1", "t", null, "c", Privacy.PUBLIC, Syntax.PLAINTEXT, null, null, PasteStatus.ACTIVE, false, List.of());
        when(cache.get("paste:p-1", PasteResult.class)).thenReturn(Optional.of(result));

        assertEquals(Optional.of(result), adapter.get("p-1"));
    }

    @Test
    void get_blank_returnsEmpty() {
        assertTrue(adapter.get("  ").isEmpty());
        assertTrue(adapter.get(null).isEmpty());
        verify(cache, never()).get(anyString(), eq(PasteResult.class));
    }

    @Test
    void put_blankPasteId_skips() {
        adapter.put(new PasteResult("  ", "t", null, "c", Privacy.PUBLIC, Syntax.PLAINTEXT, null, null, PasteStatus.ACTIVE, false, List.of()));
        adapter.put(null);

        verify(cache, never()).put(any(), any(), any());
    }
}
