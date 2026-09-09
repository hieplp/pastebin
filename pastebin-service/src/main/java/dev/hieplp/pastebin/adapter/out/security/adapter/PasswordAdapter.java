package dev.hieplp.pastebin.adapter.out.security.adapter;

import dev.hieplp.pastebin.application.port.out.password.HashPasswordPort;
import dev.hieplp.pastebin.application.port.out.password.MatchPasswordPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PasswordAdapter implements HashPasswordPort, MatchPasswordPort {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String hash(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String passwordHash) {
        return passwordEncoder.matches(rawPassword, passwordHash);
    }

}
