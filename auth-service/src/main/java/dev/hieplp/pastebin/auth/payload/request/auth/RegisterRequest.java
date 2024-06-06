package dev.hieplp.pastebin.auth.payload.request.auth;

public record RegisterRequest(
        String username,
        String name,
        String password
) {
}
