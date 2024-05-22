package dev.hieplp.pastebin.gateway.filter;

import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Supplier;

@Component
@Order(1)
@Slf4j
public class RateLimitFilter implements WebFilter {

    private Supplier<BucketConfiguration> bucketConfiguration;

    private ProxyManager<String> proxyManager;

    /**
     * Set Bucket Configuration
     *
     * @param bucketConfiguration Bucket Configuration
     */
    @Autowired
    public void setBucketConfiguration(Supplier<BucketConfiguration> bucketConfiguration) {
        this.bucketConfiguration = bucketConfiguration;
    }

    /**
     * Set Proxy Manager
     *
     * @param proxyManager Proxy Manager
     */
    @Autowired
    public void setProxyManager(ProxyManager<String> proxyManager) {
        this.proxyManager = proxyManager;
    }

    /**
     * @param exchange ServerWebExchange
     * @param chain    WebFilterChain
     * @return Mono<Void> response
     * @see <a href="https://github.com/spring-cloud/spring-cloud-gateway/issues/88#issuecomment-2016784248">Spring Boot + Bucket4j</a>
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        var remoteAddr = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostString();
        var bucket = proxyManager.builder().build(remoteAddr, bucketConfiguration);

        var probe = bucket.tryConsumeAndReturnRemaining(1);
        log.info("Remote Address: {} and Remaining tokens: {}", remoteAddr, probe.getRemainingTokens());

        if (probe.isConsumed()) {
            return chain.filter(exchange);
        } else {
            log.warn("Rate limit exceeded for {}", remoteAddr);
            exchange.getResponse().getHeaders().add("X-Rate-Limit-Retry-After-Seconds", "" + probe.getNanosToWaitForRefill());
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap("Too many requests".getBytes())));
        }
    }
}
