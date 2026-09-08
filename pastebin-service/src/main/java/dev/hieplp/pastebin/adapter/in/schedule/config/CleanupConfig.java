package dev.hieplp.pastebin.adapter.in.schedule.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CleanupProperties.class)
public class CleanupConfig {
}
