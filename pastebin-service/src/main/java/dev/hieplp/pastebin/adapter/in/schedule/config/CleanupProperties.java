package dev.hieplp.pastebin.adapter.in.schedule.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pastebin.cleanup")
public record CleanupProperties(String cron) {
}
