package dev.hieplp.pastebin.auth.service.impl;

import dev.hieplp.pastebin.auth.entity.PasswordEntity;
import dev.hieplp.pastebin.auth.service.PasswordService;
import dev.hieplp.pastebin.auth.store.PasswordStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final PasswordEncoder passwordEncoder;

    private final PasswordStore passwordStore;

    @Override
    public PasswordEntity generatePassword(String rawPassword) {
        log.info("Generate password");
        return PasswordEntity.builder()
                .password(passwordEncoder.encode(rawPassword).getBytes())
                .build();
    }

    @Override
    public PasswordEntity findById(String userId) {
        log.info("Find password by user id: {}", userId);
        return passwordStore.findById(userId);
    }
}
