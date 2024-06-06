package dev.hieplp.pastebin.auth.payload.response.auth;

import dev.hieplp.pastebin.auth.payload.response.user.UserResponse;

public record RegisterResponse(
        String username,
        String name
) {
    public RegisterResponse(UserResponse user) {
        this(user.getUsername(), user.getName());
    }
}
