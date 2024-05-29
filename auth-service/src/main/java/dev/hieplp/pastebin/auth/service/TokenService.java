package dev.hieplp.pastebin.auth.service;

import dev.hieplp.pastebin.auth.entity.UserEntity;
import dev.hieplp.pastebin.auth.payload.response.TokenResponse;
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
    TokenResponse generate(TokenType tokenType, UserEntity user);

    /**
     * Generate token
     *
     * @param tokenType   Token type
     * @param user        User entity
     * @param extraClaims Extra claims
     * @return Token response
     */
    TokenResponse generate(TokenType tokenType, UserEntity user, Map<String, Object> extraClaims);

//    /**
//     * Validate token. The token will be verified and the claims will be extracted
//     *
//     * @param tokenType Token type
//     * @param token     Token
//     * @return UserInfoDetails
//     */
//    UserInfoDetails validate(TokenType tokenType, String token);
//
//    /**
//     * Verify token
//     *
//     * @param token Token
//     * @return Jws
//     */
//    Jws<Claims> verify(String token);
}
