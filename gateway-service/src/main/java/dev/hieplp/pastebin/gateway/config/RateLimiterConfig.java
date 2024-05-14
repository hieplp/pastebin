package dev.hieplp.pastebin.gateway.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.distributed.proxy.ClientSideConfig;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.function.Supplier;

@Configuration
public class RateLimiterConfig {

    @Value("${spring.data.redis.host}")
    private String REDIS_HOST;

    @Value("${spring.data.redis.port}")
    private int REDIS_PORT;

    @Value("${spring.data.redis.password}")
    private String REDIS_PASSWORD;

    @Value("${rate.limit.max}")
    private Integer RATE_LIMIT_TIME;

    @Value("${rate.limit.bandwidth.capacity}")
    private Integer BANDWIDTH_CAPACITY;

    @Value("${rate.limit.bandwidth.refill-time}")
    private Integer BANDWIDTH_REFILL_TIME;

    @Bean
    public ProxyManager<String> lettuceProxyManager() {
        var redisClient = redisClient();
        var redisConnection = redisClient.connect(
                RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE)
        );

        var clientSideConfig = ClientSideConfig.getDefault()
                .withExpirationAfterWriteStrategy(
                        ExpirationAfterWriteStrategy.basedOnTimeForRefillingBucketUpToMax(
                                Duration.ofMinutes(RATE_LIMIT_TIME)
                        )
                );

        return LettuceBasedProxyManager.builderFor(redisConnection)
                .withClientSideConfig(clientSideConfig)
                .build();
    }

    @Bean
    public Supplier<BucketConfiguration> bucketConfiguration() {
        var bandwidth = Bandwidth.builder()
                .capacity(BANDWIDTH_CAPACITY)
                .refillGreedy(BANDWIDTH_CAPACITY, Duration.ofSeconds(BANDWIDTH_REFILL_TIME))
                .build();
        return () -> BucketConfiguration.builder()
                .addLimit(bandwidth)
                .build();
    }

    private RedisClient redisClient() {
        return RedisClient.create(RedisURI.builder()
                .withHost(REDIS_HOST)
                .withPort(REDIS_PORT)
                .withPassword(REDIS_PASSWORD.toCharArray())
                .build());
    }
}
