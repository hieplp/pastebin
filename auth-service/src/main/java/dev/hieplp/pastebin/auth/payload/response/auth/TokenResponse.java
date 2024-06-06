package dev.hieplp.pastebin.auth.payload.response.auth;

import lombok.Builder;

import java.util.Date;

@Builder
public record TokenResponse(
        String token,
        Date expiredAt
) {
}
