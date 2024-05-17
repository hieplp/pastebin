package dev.hieplp.pastebin.key.store.impl;

import dev.hieplp.pastebin.key.entity.UsedKeyEntity;
import dev.hieplp.pastebin.key.repository.UsedKeyRepository;
import dev.hieplp.pastebin.key.store.UsedKeyStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsedKeyStoreImpl implements UsedKeyStore {

    private final UsedKeyRepository usedKeyRepo;

    @Override
    public Set<String> findAllByKeyIn(Set<String> key) {
        log.debug("Find all by key in {}", key);
        return usedKeyRepo.findAllByKeyIn(key).stream()
                .map(UsedKeyEntity::getKey)
                .collect(Collectors.toSet());
    }

    @Override
    public void save(UsedKeyEntity key) {
        log.debug("Save used key: {}", key);
        usedKeyRepo.save(key);
    }
}
