package dev.hieplp.pastebin.application.dto.token;

import java.time.Duration;

public record TokenIssue(
        String token,
        Duration ttl
) {
}
