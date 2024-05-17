package dev.hieplp.pastebin.key.service.impl;

import dev.hieplp.pastebin.key.service.UnusedKeyService;
import dev.hieplp.pastebin.key.service.UsedKeyService;
import dev.hieplp.pastebin.key.util.KeyUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UnusedKeyServiceImpl implements UnusedKeyService {

    /**
     * Set of unused keys
     */
    private static final Set<String> UNUSED_KEYS = new HashSet<>();

    /**
     * Total prepared keys
     */
    private static final int TOTAL_PREPARED_KEYS = 10;

    // --------------------------------------------------
    // Dependencies
    // --------------------------------------------------

    private final UsedKeyService usedKeyService;

    @PostConstruct
    public void setup() {
        log.info("Prepare {} unused keys", TOTAL_PREPARED_KEYS);
        generateUnusedKeys(TOTAL_PREPARED_KEYS);
        log.info("Prepared {} unused keys: {}", UNUSED_KEYS.size(), UNUSED_KEYS);
    }

    @Override
    public void generateUnusedKeys(int total) {
        log.info("Generate {} unused keys", total);
        generateUnusedKeys(total, null);
    }

    @Override
    public void generateUnusedKeys(int total, String excludedKey) {
        log.info("Generate {} unused keys with excludedKey: {}", total, excludedKey);

        // Generate keys
        for (int i = 0; i < total; i++) {
            String key = null;
            // Generate a key without any condition
            if (excludedKey == null) {
                key = KeyUtil.generateKey();
            }
            // Generate a key that is not equal to the excluded key
            else {
                do {
                    key = KeyUtil.generateKey();
                } while (excludedKey.equals(key) || UNUSED_KEYS.contains(key));
            }

            UNUSED_KEYS.add(key);
        }

        // Find used keys
        var usedKeys = usedKeyService.findAllByKeyIn(UNUSED_KEYS);

        // If there are no used keys, return
        if (usedKeys.isEmpty()) {
            return;
        }

        // Generate more keys to replace used keys
        generateUnusedKeys(usedKeys.size(), excludedKey);
    }

    @Override
    public String getUnusedKey() {
        log.info("Get an unused key");

        final var optionalKey = UNUSED_KEYS.stream().findFirst();

        // If there is no unused key, generate a new one
        if (optionalKey.isEmpty()) {
            generateUnusedKeys(1);
            return getUnusedKey();
        }

        final var key = optionalKey.get();

        // Remove the used key
        UNUSED_KEYS.remove(key);

        // Save the used key
        usedKeyService.save(key);

        // Add more keys to replace the used key
        generateUnusedKeys(1, key);

        return key;
    }
}
