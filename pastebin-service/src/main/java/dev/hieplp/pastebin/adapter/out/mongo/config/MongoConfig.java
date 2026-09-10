package dev.hieplp.pastebin.adapter.out.mongo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.data.mongodb.autoconfigure.DataMongoAutoConfiguration;
import org.springframework.boot.mongodb.autoconfigure.MongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ConditionalOnProperty(name = "pastebin.persistence.type", havingValue = "mongo")
@Import({
        MongoAutoConfiguration.class,
        DataMongoAutoConfiguration.class
})
@EnableMongoRepositories(basePackages = "dev.hieplp.pastebin.adapter.out.mongo.repository")
public class MongoConfig {
}
