package dev.hieplp.pastebin.auth.store;

import dev.hieplp.pastebin.auth.entity.UserEntity;

public interface UserStore {
    UserEntity save(UserEntity user);

    boolean existsByUsername(String username);

    UserEntity findByUsername(String username);
}
