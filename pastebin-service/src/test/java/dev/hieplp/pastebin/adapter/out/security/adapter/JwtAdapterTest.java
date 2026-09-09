package dev.hieplp.pastebin.adapter.out.security.adapter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import dev.hieplp.pastebin.adapter.out.security.config.JwtProperties;
import dev.hieplp.pastebin.domain.enums.Role;
import dev.hieplp.pastebin.domain.enums.TokenType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class JwtAdapterTest {

    private JwtAdapter jwtAdapter;

    @BeforeEach
    void setUp() throws JOSEException {
        var rsaKey = new RSAKeyGenerator(2048).keyID("test").generate();
        var props = new JwtProperties(
                "pastebin",
                Duration.ofMinutes(15),
                Duration.ofDays(7),
                "access_token",
                "refresh_token",
                false,
                "Lax",
                "/",
                "classpath:jwt/private.pem",
                "classpath:jwt/public.pem"
        );
        jwtAdapter = new JwtAdapter(props, rsaKey);
    }

    @Test
    void issue_and_parse_roundTripsAccessToken() {
        var issued = jwtAdapter.issue("root-1", Role.ROOT, TokenType.ACCESS);

        var claims = jwtAdapter.parse(issued.token()).orElseThrow();
        assertEquals("root-1", claims.subject());
        assertEquals(Role.ROOT, claims.role());
        assertEquals(TokenType.ACCESS, claims.type());
        assertEquals(Duration.ofMinutes(15), issued.ttl());
    }

    @Test
    void parse_junk_returnsEmpty() {
        assertTrue(jwtAdapter.parse("not-a-jwt").isEmpty());
        assertTrue(jwtAdapter.parse(null).isEmpty());
    }

}
