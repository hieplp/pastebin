package dev.hieplp.pastebin.auth.service;

import dev.hieplp.pastebin.auth.payload.response.auth.TokenResponse;
import dev.hieplp.pastebin.auth.payload.response.user.UserResponse;
import dev.hieplp.pastebin.common.enums.token.TokenType;

import java.util.Map;

public interface TokenService {
    /**
     * Generate token
     *
     * @param tokenType Token type
     * @param user      User entity
     * @return Token response
     */
    TokenResponse generate(TokenType tokenType, UserResponse user);

    /**
     * Generate token
     *
     * @param tokenType   Token type
     * @param user        User entity
     * @param extraClaims Extra claims
     * @return Token response
     */
    TokenResponse generate(TokenType tokenType, UserResponse user, Map<String, Object> extraClaims);
}
