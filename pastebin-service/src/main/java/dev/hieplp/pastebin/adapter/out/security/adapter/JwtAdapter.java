package dev.hieplp.pastebin.adapter.out.security.adapter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dev.hieplp.pastebin.adapter.out.security.config.JwtProperties;
import dev.hieplp.pastebin.application.dto.token.TokenClaims;
import dev.hieplp.pastebin.application.dto.token.TokenIssue;
import dev.hieplp.pastebin.application.port.out.token.IssueTokenPort;
import dev.hieplp.pastebin.application.port.out.token.ParseTokenPort;
import dev.hieplp.pastebin.domain.enums.Role;
import dev.hieplp.pastebin.domain.enums.TokenType;
import dev.hieplp.pastebin.domain.exception.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.text.ParseException;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Repository
public class JwtAdapter implements IssueTokenPort, ParseTokenPort {

    private static final String CLAIM_ROLE = "role";
    private static final String CLAIM_TYPE = "type";

    private final JwtProperties props;

    private final RSASSASigner signer;
    private final RSASSAVerifier verifier;

    private final String keyId;

    public JwtAdapter(JwtProperties props, RSAKey rsaKey) {
        this.props = props;
        this.keyId = rsaKey.getKeyID();
        try {
            this.signer = new RSASSASigner(rsaKey);
            this.verifier = new RSASSAVerifier(rsaKey.toRSAPublicKey());
        } catch (JOSEException e) {
            throw new JwtException("Failed to init RSA JWT signer", e);
        }
    }

    @Override
    public TokenIssue issue(String subject, Role role, TokenType type) {
        var ttl = type == TokenType.ACCESS ? props.accessTtl() : props.refreshTtl();
        var now = Instant.now();
        var claims = new JWTClaimsSet.Builder()
                .subject(subject)
                .issuer(props.issuer())
                .issueTime(Date.from(now))
                .expirationTime(Date.from(now.plus(ttl)))
                .claim(CLAIM_ROLE, role.name())
                .claim(CLAIM_TYPE, type.name())
                .build();

        try {
            var jwt = new SignedJWT(
                    new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(keyId).build(),
                    claims
            );
            jwt.sign(signer);
            return new TokenIssue(jwt.serialize(), ttl);
        } catch (JOSEException e) {
            throw new JwtException("Failed to sign JWT", e);
        }
    }

    @Override
    public Optional<TokenClaims> parse(String token) {
        if (token == null || token.isBlank()) {
            return Optional.empty();
        }

        try {
            var jwt = SignedJWT.parse(token);
            if (!jwt.verify(verifier)) {
                return Optional.empty();
            }

            var claims = jwt.getJWTClaimsSet();
            var exp = claims.getExpirationTime();
            if (exp == null || exp.toInstant().isBefore(Instant.now())) {
                return Optional.empty();
            }
            if (props.issuer() != null && !props.issuer().equals(claims.getIssuer())) {
                return Optional.empty();
            }

            return Optional.of(new TokenClaims(
                    claims.getSubject(),
                    Role.valueOf(claims.getStringClaim(CLAIM_ROLE)),
                    TokenType.valueOf(claims.getStringClaim(CLAIM_TYPE))
            ));
        } catch (ParseException | JOSEException | IllegalArgumentException e) {
            log.debug("JWT parse failed: {}", e.getMessage());
            return Optional.empty();
        }
    }


}
