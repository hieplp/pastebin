package dev.hieplp.pastebin.adapter.out.persistence.config;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "dev.hieplp.pastebin.adapter.out.persistence.repository")
@EntityScan(basePackages = "dev.hieplp.pastebin.adapter.out.persistence.entity")
@EnableJpaAuditing
public class JpaConfig {
}
