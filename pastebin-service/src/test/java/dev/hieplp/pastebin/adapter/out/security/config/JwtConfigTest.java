package dev.hieplp.pastebin.adapter.out.security.config;

import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtConfigTest {

    @Test
    void rsaKey_loadsFromClasspathPems() throws JOSEException {
        var key = new JwtConfig(props("classpath:jwt/private.pem", "classpath:jwt/public.pem"), new DefaultResourceLoader()).rsaKey();
        assertNotNull(key.toRSAPrivateKey());
        assertNotNull(key.toRSAPublicKey());
    }

    @Test
    void rsaKey_missingPem_throws() {
        var config = new JwtConfig(props("classpath:jwt/missing.pem", "classpath:jwt/public.pem"), new DefaultResourceLoader());
        assertThrows(IllegalStateException.class, config::rsaKey);
    }

    private static JwtProperties props(String privateKey, String publicKey) {
        return new JwtProperties(
                "pastebin",
                Duration.ofMinutes(15),
                Duration.ofDays(7),
                "access_token",
                "refresh_token",
                false,
                "Lax",
                "/",
                privateKey,
                publicKey
        );
    }

}
