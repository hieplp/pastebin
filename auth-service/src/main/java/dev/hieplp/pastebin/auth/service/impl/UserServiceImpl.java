package dev.hieplp.pastebin.auth.service.impl;

import dev.hieplp.pastebin.auth.entity.UserEntity;
import dev.hieplp.pastebin.auth.payload.response.UserResponse;
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
    public UserEntity findByUserId(String userId) {
        log.info("Find user by userId: {}", userId);
        return userStore.findByUserId(userId);
    }

    @Override
    public boolean existsByUsername(String username) {
        log.info("Check if username exists: {} in database", username);
        return userStore.existsByUsername(username);
    }

    @Override
    public UserResponse getProfile(String userId) {
        log.info("Get profile of user: {}", userId);
        var userEntity = findByUserId(userId);
        return new UserResponse(userEntity);
    }
}
