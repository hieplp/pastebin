package dev.hieplp.pastebin.adapter.out.localstorage.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pastebin.storage.local")
public record LocalStorageProperties(String root) {
}
