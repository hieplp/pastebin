package dev.hieplp.pastebin.auth.payload.response;

import lombok.Builder;

import java.util.Date;

@Builder
public record TokenResponse(
        String token,
        Date expiredAt
) {
}
