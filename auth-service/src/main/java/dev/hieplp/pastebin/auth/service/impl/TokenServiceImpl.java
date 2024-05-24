package dev.hieplp.pastebin.auth.service.impl;

import dev.hieplp.pastebin.auth.config.UserInfoDetails;
import dev.hieplp.pastebin.auth.entity.UserEntity;
import dev.hieplp.pastebin.auth.payload.response.TokenResponse;
import dev.hieplp.pastebin.auth.service.TokenService;
import dev.hieplp.pastebin.common.enums.token.TokenClaimKey;
import dev.hieplp.pastebin.common.enums.token.TokenType;
import dev.hieplp.pastebin.common.exception.UnauthorizedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    public TokenResponse generate(TokenType tokenType, UserEntity user, Map<String, Object> extraClaims) {
        return TokenType.REFRESH.equals(tokenType)
                ? buildToken(user, tokenType, extraClaims, refreshTokenExpiration)
                : buildToken(user, tokenType, extraClaims, accessTokenExpiration);
    }

    @Override
    public UserInfoDetails validate(TokenType tokenType, String token) {
        log.info("Validate token: {} with type: {}", token, tokenType);

        final var jws = verify(token);

        final var claims = jws.getPayload();

        final var type = extractType(claims);
        if (ObjectUtils.isEmpty(type) || ObjectUtils.notEqual(tokenType.name(), type)) {
            log.warn("Token type is invalid: {}", type);
            throw new UnauthorizedException("Token type is invalid");
        }

        final var userId = extractUserId(claims);

        return new UserInfoDetails(UserEntity.builder()
                .userId(userId)
                .build()
        );
    }

    @Override
    public Jws<Claims> verify(String token) {
        try {
            final var parser = Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build();
            return parser.parseSignedClaims(token);
        } catch (ExpiredJwtException ex) {
            log.warn("JWT expired: {}", ex.getMessage());
            throw new UnauthorizedException("JWT expired");
        } catch (IllegalArgumentException ex) {
            log.warn("Token is null, empty or only whitespace: {}", ex.getMessage());
            throw new UnauthorizedException("Token is null, empty or only whitespace");
        } catch (MalformedJwtException ex) {
            log.warn("JWT is invalid", ex);
            throw new UnauthorizedException("JWT is invalid");
        } catch (UnsupportedJwtException ex) {
            log.warn("JWT is not supported", ex);
            throw new UnauthorizedException("JWT is not supported");
        }
    }

    private String extractType(Claims claims) {
        return claims.get(TokenClaimKey.TYPE.getKey(), String.class);
    }

    private String extractUserId(Claims claims) {
        return claims.get(TokenClaimKey.USER_ID.getKey(), String.class);
    }

    private String extractSubject(Claims claims) {
        return claims.getSubject();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private TokenResponse buildToken(UserEntity user,
                                     TokenType tokenType,
                                     Map<String, Object> extraClaims,
                                     long expiredIn) {
        return new TokenBuilder()
                .user(user)
                .extraClaims(extraClaims)
                .expiredIn(expiredIn)
                .tokenType(tokenType)
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

            extraClaims.put(TokenClaimKey.USER_ID.getKey(), user.getUserId());
            extraClaims.put(TokenClaimKey.TYPE.getKey(), tokenType.name());

            var expiredAt = new Date(System.currentTimeMillis() + expiredIn);

            var token = Jwts.builder()
                    .id(UUID.randomUUID().toString())
                    .subject(user.getUserId())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(expiredAt)
                    .claims(extraClaims)
                    .signWith(signedKey)
                    .compact();

            return TokenResponse.builder()
                    .token(token)
                    .expiredAt(expiredAt)
                    .build();
        }
    }

}
