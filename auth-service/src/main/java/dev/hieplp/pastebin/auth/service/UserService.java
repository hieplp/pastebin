package dev.hieplp.pastebin.auth.service;

import dev.hieplp.pastebin.auth.entity.UserEntity;
import dev.hieplp.pastebin.auth.payload.response.UserResponse;

public interface UserService {
    UserEntity save(UserEntity user);

    UserEntity findByUsername(String username);

    UserEntity findByUserId(String userId);

    boolean existsByUsername(String username);

    UserResponse getProfile(String userId);
}
