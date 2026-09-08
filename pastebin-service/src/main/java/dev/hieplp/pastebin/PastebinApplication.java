package dev.hieplp.pastebin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PastebinApplication {

    static void main(String[] args) {
        SpringApplication.run(PastebinApplication.class, args);
    }

}
