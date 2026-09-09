package dev.hieplp.pastebin.application.service.root;

import dev.hieplp.pastebin.application.port.in.root.InitRootTokenUseCase;
import dev.hieplp.pastebin.application.port.out.root.ExistRootPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HexFormat;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitRootTokenService implements InitRootTokenUseCase {

    private final ExistRootPort existRootPort;
    private final AtomicReference<String> token = new AtomicReference<>();

    @Override
    public Optional<String> init() {
        log.info("Init root token");

        if (existRootPort.exists()) {
            log.info("Root already exists; skip init token");
            token.set(null);
            return Optional.empty();
        }

        var current = token.get();
        if (current != null) {
            log.info("Root init token already present; skip generate");
            return Optional.empty();
        }

        var generated = generate();
        token.set(generated);
        log.info("Root init token generated");

        return Optional.of(generated);
    }

    @Override
    public boolean matches(String candidate) {
        var expected = token.get();
        if (expected == null || candidate == null) {
            return false;
        }
        return MessageDigest.isEqual(
                expected.getBytes(StandardCharsets.UTF_8),
                candidate.getBytes(StandardCharsets.UTF_8)
        );
    }

    @Override
    public void consume() {
        token.set(null);
        log.info("Root init token consumed");
    }

    private static String generate() {
        var bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return HexFormat.of().formatHex(bytes);
    }

}
