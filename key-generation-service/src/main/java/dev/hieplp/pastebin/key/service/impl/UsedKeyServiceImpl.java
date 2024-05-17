package dev.hieplp.pastebin.key.service.impl;

import dev.hieplp.pastebin.key.entity.UsedKeyEntity;
import dev.hieplp.pastebin.key.service.UsedKeyService;
import dev.hieplp.pastebin.key.store.UsedKeyStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UsedKeyServiceImpl implements UsedKeyService {

    private final UsedKeyStore usedKeyStore;

    @Override
    public Set<String> findAllByKeyIn(Set<String> key) {
        log.info("Find all by key in {}", key);
        return usedKeyStore.findAllByKeyIn(key);
    }

    @Override
    public void save(String key) {
        log.info("Save used key: {}", key);
        final var entity = UsedKeyEntity.builder()
                .key(key)
                .build();
        usedKeyStore.save(entity);
    }
}
