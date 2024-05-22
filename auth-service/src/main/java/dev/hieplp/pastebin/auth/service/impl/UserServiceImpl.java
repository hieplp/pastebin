package dev.hieplp.pastebin.auth.service.impl;

import dev.hieplp.pastebin.auth.entity.UserEntity;
import dev.hieplp.pastebin.auth.service.UserService;
import dev.hieplp.pastebin.auth.store.UserStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserStore userStore;

    @Override
    public UserEntity save(UserEntity user) {
        log.info("Save user: {}", user);
        return userStore.save(user);
    }

    @Override
    public UserEntity findByUsername(String username) {
        log.info("Find user by username: {}", username);
        return userStore.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        log.info("Check if username exists: {} in database", username);
        return userStore.existsByUsername(username);
    }
}
