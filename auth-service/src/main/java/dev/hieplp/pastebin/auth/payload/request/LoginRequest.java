package dev.hieplp.pastebin.auth.payload.request;

public record LoginRequest(
        String username,
        String password
) {
}
