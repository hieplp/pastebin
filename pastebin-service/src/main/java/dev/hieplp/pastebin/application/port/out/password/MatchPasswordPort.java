package dev.hieplp.pastebin.application.port.out.password;

public interface MatchPasswordPort {

    boolean matches(String rawPassword, String passwordHash);

}
