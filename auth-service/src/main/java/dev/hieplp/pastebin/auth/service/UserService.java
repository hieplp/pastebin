package dev.hieplp.pastebin.auth.service;

import dev.hieplp.pastebin.auth.entity.UserEntity;

public interface UserService {
    UserEntity save(UserEntity user);

    UserEntity findByUsername(String username);

    boolean existsByUsername(String username);
}
