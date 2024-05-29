package dev.hieplp.pastebin.common.auth;

import dev.hieplp.pastebin.common.enums.token.TokenClaimKey;
import dev.hieplp.pastebin.common.enums.token.TokenType;
import dev.hieplp.pastebin.common.exception.UnauthorizedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;

@Slf4j
public class TokenUtil {
    public static UserInfoDetails validate(String secret,
                                           TokenType tokenType,
                                           String token) {
        log.info("Validate token: {} with type: {}", token, tokenType);

        final var jws = verify(secret, token);

        final var claims = jws.getPayload();


        if (token == null) {
            log.warn("Token is empty");
            throw new UnauthorizedException("Token is empty");
        }

        final var type = extractType(claims);
        if (!tokenType.name().equals(type)) {
            log.warn("Token type is invalid: {}", type);
            throw new UnauthorizedException("Token type is invalid");
        }

        final var userId = extractUserId(claims);

        return UserInfoDetails.builder()
                .userId(userId)
                .build();
    }

    public static Jws<Claims> verify(String secret, String token) {
        try {
            final var parser = Jwts.parser()
                    .verifyWith(getSignInKey(secret))
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
        } catch (SignatureException ex) {
            log.warn("JWT signature is invalid", ex);
            throw new UnauthorizedException("JWT signature is invalid");
        }
    }

    public static String extractType(Claims claims) {
        return claims.get(TokenClaimKey.TYPE.getKey(), String.class);
    }

    public static String extractUserId(Claims claims) {
        return claims.get(TokenClaimKey.USER_ID.getKey(), String.class);
    }

    public static String extractSubject(Claims claims) {
        return claims.getSubject();
    }

    public static SecretKey getSignInKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
