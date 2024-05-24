package dev.hieplp.pastebin.auth.service;

import dev.hieplp.pastebin.auth.config.UserInfoDetails;
import dev.hieplp.pastebin.auth.entity.UserEntity;
import dev.hieplp.pastebin.auth.payload.response.TokenResponse;
import dev.hieplp.pastebin.common.enums.token.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.Map;

public interface TokenService {
    TokenResponse generate(TokenType tokenType, UserEntity user);

    TokenResponse generate(TokenType tokenType, UserEntity user, Map<String, Object> extraClaims);

    UserInfoDetails validate(TokenType tokenType, String token);

    Jws<Claims> verify(String token);
}
