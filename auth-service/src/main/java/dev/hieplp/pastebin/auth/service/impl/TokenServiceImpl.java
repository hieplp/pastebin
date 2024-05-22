package dev.hieplp.pastebin.auth.service.impl;

import dev.hieplp.pastebin.auth.entity.UserEntity;
import dev.hieplp.pastebin.auth.payload.response.TokenResponse;
import dev.hieplp.pastebin.auth.service.TokenService;
import dev.hieplp.pastebin.common.enums.token.TokenType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.access-token.expiration}")
    private Long accessTokenExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private Long refreshTokenExpiration;

    @Override
    public TokenResponse generate(TokenType tokenType, UserEntity user) {
        return generate(tokenType, user, new HashMap<>());
    }

    @Override
    public TokenResponse generate(TokenType tokenType,
                                  UserEntity user,
                                  Map<String, Object> extraClaims) {
        if (TokenType.REFRESH.equals(tokenType)) {
            return buildToken(user, extraClaims, refreshTokenExpiration);
        }
        return buildToken(user, extraClaims, accessTokenExpiration);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private TokenResponse buildToken(UserEntity user,
                                     Map<String, Object> extraClaims,
                                     long expiredIn) {
        return new TokenBuilder()
                .user(user)
                .extraClaims(extraClaims)
                .expiredIn(expiredIn)
                .tokenType(TokenType.ACCESS)
                .signWith(getSignInKey())
                .build();
    }

    static class TokenBuilder {
        private Map<String, Object> extraClaims;

        private UserEntity user;

        private long expiredIn;

        private TokenType tokenType;

        private Key signedKey;

        public TokenBuilder extraClaims(Map<String, Object> extraClaims) {
            this.extraClaims = extraClaims;
            return this;
        }

        public TokenBuilder user(UserEntity user) {
            this.user = user;
            return this;
        }

        public TokenBuilder expiredIn(long expiredIn) {
            this.expiredIn = expiredIn;
            return this;
        }

        public TokenBuilder tokenType(TokenType tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        public TokenBuilder signWith(Key signedKey) {
            this.signedKey = signedKey;
            return this;
        }

        public TokenResponse build() {
            if (extraClaims == null) {
                extraClaims = new HashMap<>();
            }
            extraClaims.put("tokenType", tokenType.name());

            var expiredAt = new Date(System.currentTimeMillis() + expiredIn);

            var token = Jwts.builder()
                    .claims(extraClaims)
                    .subject(user.getUsername())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(expiredAt)
                    .signWith(signedKey)
                    .compact();

            return TokenResponse.builder()
                    .token(token)
                    .expiredAt(expiredAt)
                    .build();
        }
    }

}
