package dev.hieplp.pastebin.application.dto.auth;

import java.time.Duration;

public record LoginResult(
        String username,
        String accessToken,
        Duration accessTtl,
        String refreshToken,
        Duration refreshTtl
) {
}
