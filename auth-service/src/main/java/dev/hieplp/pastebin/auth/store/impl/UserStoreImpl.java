package dev.hieplp.pastebin.auth.store.impl;

import dev.hieplp.pastebin.auth.entity.UserEntity;
import dev.hieplp.pastebin.auth.repository.UserRepository;
import dev.hieplp.pastebin.auth.store.UserStore;
import dev.hieplp.pastebin.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {

    private final UserRepository userRepo;

    @Override
    public UserEntity save(UserEntity user) {
        log.info("Save user: {}", user);
        return userRepo.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        log.info("Check if username exists: {} in database", username);
        return userRepo.existsByUsername(username);
    }

    @Override
    public UserEntity findByUsername(String username) {
        log.info("Find user by username: {}", username);
        return userRepo.findByUsername(username)
                .orElseThrow(() -> {
                    final var msg = "User not found with username: " + username;
                    log.error(msg);
                    return new NotFoundException(msg);
                });
    }

    @Override
    public UserEntity findByUserId(String userId) {
        log.info("Find user by userId: {}", userId);
        return userRepo.findById(userId)
                .orElseThrow(() -> {
                    final var msg = "User not found with userId: " + userId;
                    log.error(msg);
                    return new NotFoundException(msg);
                });
    }
}
