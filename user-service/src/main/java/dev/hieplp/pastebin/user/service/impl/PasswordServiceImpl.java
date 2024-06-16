package dev.hieplp.pastebin.user.service.impl;

import dev.hieplp.pastebin.user.entity.PasswordEntity;
import dev.hieplp.pastebin.user.payload.response.PasswordResponse;
import dev.hieplp.pastebin.user.service.PasswordService;
import dev.hieplp.pastebin.user.store.PasswordStore;
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
    public PasswordResponse findByUserId(String userId) {
        log.info("Find password by userId: {}", userId);
        var entity = passwordStore.findById(userId);
        return PasswordResponse.builder()
                .userId(entity.getUserId())
                .password(new String(entity.getPassword()))
                .build();
    }
}
