package dev.hieplp.pastebin.auth.payload.request;

public record RegisterRequest(
        String username,
        String name,
        String password
) {
}
