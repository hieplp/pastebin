package dev.hieplp.pastebin.application.port.out.token;

import dev.hieplp.pastebin.application.dto.token.TokenClaims;

import java.util.Optional;

public interface ParseTokenPort {

    Optional<TokenClaims> parse(String token);

}
