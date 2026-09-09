package dev.hieplp.pastebin.adapter.in.web.payload.auth;

import java.time.Duration;

public record LoginResponse(
        String username,
        Duration accessTtl,
        Duration refreshTtl
) {
}
