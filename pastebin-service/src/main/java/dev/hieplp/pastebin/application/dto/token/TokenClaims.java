package dev.hieplp.pastebin.application.dto.token;

import dev.hieplp.pastebin.domain.enums.Role;
import dev.hieplp.pastebin.domain.enums.TokenType;

public record TokenClaims(
        String subject,
        Role role,
        TokenType type
) {
}
