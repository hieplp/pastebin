package dev.hieplp.pastebin.adapter.out.jpa.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.flyway.autoconfigure.FlywayAutoConfiguration;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ConditionalOnProperty(name = "pastebin.persistence.type", havingValue = "jpa", matchIfMissing = true)
@Import({
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        FlywayAutoConfiguration.class
})
@EnableJpaRepositories(basePackages = "dev.hieplp.pastebin.adapter.out.jpa.repository")
@EntityScan(basePackages = "dev.hieplp.pastebin.adapter.out.jpa.entity")
@EnableJpaAuditing
public class JpaConfig {
}
