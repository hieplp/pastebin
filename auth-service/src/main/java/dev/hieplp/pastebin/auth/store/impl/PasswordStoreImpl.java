package dev.hieplp.pastebin.auth.store.impl;

import dev.hieplp.pastebin.auth.entity.PasswordEntity;
import dev.hieplp.pastebin.auth.repository.PasswordRepository;
import dev.hieplp.pastebin.auth.store.PasswordStore;
import dev.hieplp.pastebin.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordStoreImpl implements PasswordStore {

    private final PasswordRepository passwordRepo;

    @Override
    public PasswordEntity findById(String id) {
        log.info("Find password by user id: {}", id);
        return passwordRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Password not found with user id: {}", id);
                    return new NotFoundException("Password not found");
                });
    }
}
