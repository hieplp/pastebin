package dev.hieplp.pastebin.application.port.out.password;

public interface HashPasswordPort {

    String hash(String rawPassword);

}
