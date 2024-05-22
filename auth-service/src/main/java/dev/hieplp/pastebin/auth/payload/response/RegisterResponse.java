package dev.hieplp.pastebin.auth.payload.response;

import dev.hieplp.pastebin.auth.entity.UserEntity;

public record RegisterResponse(
        String username,
        String name
) {
    public RegisterResponse(UserEntity userEntity) {
        this(userEntity.getUsername(), userEntity.getName());
    }
}
