package dev.hieplp.pastebin.adapter.out.s3.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pastebin.storage.s3")
public record S3StorageProperties(
        String bucket,
        String region,
        String accessKey,
        String secretKey,
        String endpoint,
        Boolean pathStyle
) {
}
