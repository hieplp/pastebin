package dev.hieplp.pastebin.auth.payload.request.auth;

public record LoginRequest(
        String username,
        String password
) {
}
