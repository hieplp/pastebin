package dev.hieplp.pastebin.paste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PasteApplication {

    public static void main(String[] args) {
        SpringApplication.run(PasteApplication.class, args);
    }

}
