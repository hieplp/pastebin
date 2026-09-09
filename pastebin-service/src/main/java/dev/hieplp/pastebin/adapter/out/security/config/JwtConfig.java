package dev.hieplp.pastebin.adapter.out.security.config;

import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@RequiredArgsConstructor
public class JwtConfig {

    private static final String KEY_ID = "pastebin";

    private final JwtProperties props;
    private final ResourceLoader resourceLoader;

    @Bean
    RSAKey rsaKey() {
        var privateKey = readPrivate(readPem(props.privateKey()));
        var publicKey = readPublic(readPem(props.publicKey()));
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(KEY_ID)
                .build();
    }

    private String readPem(String location) {
        var resource = resourceLoader.getResource(location);

        if (!resource.exists()) {
            throw new IllegalStateException("RSA PEM not found: " + location);
        }

        try {
            return resource.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read RSA PEM: " + location, e);
        }
    }

    private static RSAPrivateKey readPrivate(String pem) {
        try {
            var spec = new PKCS8EncodedKeySpec(decode(pem, "PRIVATE KEY"));
            return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(spec);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse RSA private PEM", e);
        }
    }

    private static RSAPublicKey readPublic(String pem) {
        try {
            var spec = new X509EncodedKeySpec(decode(pem, "PUBLIC KEY"));
            return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse RSA public PEM", e);
        }
    }

    private static byte[] decode(String pem, String type) {
        var stripped = pem
                .replace("-----BEGIN " + type + "-----", "")
                .replace("-----END " + type + "-----", "")
                .replaceAll("\\s", "");
        return Base64.getDecoder().decode(stripped);
    }

}
