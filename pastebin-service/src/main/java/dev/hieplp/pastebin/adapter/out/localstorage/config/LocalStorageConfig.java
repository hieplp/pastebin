package dev.hieplp.pastebin.adapter.out.localstorage.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "pastebin.storage.type", havingValue = "local", matchIfMissing = true)
@EnableConfigurationProperties(LocalStorageProperties.class)
public class LocalStorageConfig {
}
