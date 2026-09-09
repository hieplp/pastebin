package dev.hieplp.pastebin.adapter.out.redis.adapter;

import dev.hieplp.pastebin.adapter.out.redis.config.PasteCacheProperties;
import dev.hieplp.pastebin.adapter.out.redis.support.RedisJsonCache;
import dev.hieplp.pastebin.application.dto.paste.result.PasteResult;
import dev.hieplp.pastebin.application.port.out.paste.CachePastePort;
import dev.hieplp.pastebin.domain.util.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisPasteCacheAdapter implements CachePastePort {

    private static final Duration DEFAULT_TTL = Duration.ofHours(1);

    private final PasteCacheProperties props;

    private final RedisJsonCache cache;

    @Override
    public Optional<PasteResult> get(String idOrAlias) {
        var id = Strings.trim(idOrAlias).orElse(null);
        if (id == null) {
            log.debug("Cache get skipped: blank idOrAlias");
            return Optional.empty();
        }

        var result = cache.get(key(id), PasteResult.class);
        log.debug(result.isPresent() ? "Cache hit idOrAlias={}" : "Cache miss idOrAlias={}", id);
        return result;
    }

    @Override
    public void put(PasteResult result) {
        if (result == null || Strings.trim(result.pasteId()).isEmpty()) {
            log.debug("Cache put skipped: missing pasteId");
            return;
        }

        var ttl = ttl(result);
        log.info("Cache put pasteId={} alias={} ttl={}", result.pasteId(), result.alias(), ttl);
        cache.put(
                result,
                ttl,
                key(result.pasteId().trim()),
                aliasKey(result.alias())
        );
    }

    @Override
    public void evict(String idOrAlias) {
        var id = Strings.trim(idOrAlias).orElse(null);
        if (id == null) {
            log.debug("Cache evict skipped: blank idOrAlias");
            return;
        }

        var keys = new ArrayList<String>();
        keys.add(key(id));
        get(id).ifPresent(cached -> {
            keys.add(key(cached.pasteId()));
            var aliasKey = aliasKey(cached.alias());
            if (aliasKey != null) {
                keys.add(aliasKey);
            }
        });
        log.info("Cache evict idOrAlias={} keys={}", id, keys);
        cache.delete(keys);
    }

    private Duration ttl(PasteResult result) {
        var defaultTtl = props.ttl() != null ? props.ttl() : DEFAULT_TTL;
        if (result.expiredAt() == null) {
            return defaultTtl;
        }

        var untilExpiry = Duration.between(Instant.now(), result.expiredAt());
        if (untilExpiry.compareTo(defaultTtl) < 0) {
            return untilExpiry;
        }

        return defaultTtl;
    }

    private static String key(String idOrAlias) {
        return "paste:" + idOrAlias;
    }

    private static String aliasKey(String alias) {
        return Strings.trim(alias).map(RedisPasteCacheAdapter::key).orElse(null);
    }

}